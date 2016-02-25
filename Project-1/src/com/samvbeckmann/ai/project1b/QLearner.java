package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.Action;
import com.samvbeckmann.ai.ExpectedUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Defines an agent, which, given a world and transition model,
 * determines a utility map for the state space.
 *
 * @author Sam Beckmann
 */
public class QLearner
{
    private Map<Feature, Double> utilities;
    private World world;
    private TransitionModel transitions;
    private double discount;
    private double lambda;
    private double learningRate;
    private Random rnd = new Random();

    public QLearner(World world, TransitionModel transitions, double discount, double lambda, double learningRate)
    {
        this.world = world;
        this.transitions = transitions;
        this.discount = discount;
        this.lambda = lambda;
        this.learningRate = learningRate;
        utilities = new HashMap<>();
    }

    /**
     * Perform a full episode in the world,
     * until a terminal state is reached.
     * Update utilities for features with new knowledge.
     *
     * @return Reward for this episode
     */
    public double performEpisode(double epsilon)
    {
        double totalReward = 0;
        Coordinate position = world.getStartingPosition();
        Action action = Action.randomAction(); // TODO: Change, to remove all randomness
        Map<Feature, Double> eligibility = new HashMap<>();

        while (!world.isTerminal(position))
        {
            Map<Feature, Double> currentFeatures = world.getActiveFeatures(position, action);
            for (Feature feature : currentFeatures.keySet())
            {
                Double eValue = eligibility.get(feature);
                if (eValue == null)
                    eValue = 0D;
                eligibility.put(feature, eValue + currentFeatures.get(feature));
            }

            position = world.normalizeCoordinate(transitions.getNewCoordinate(position, action));
            double lastReward = world.getReward(position);
            totalReward += lastReward;

            double delta = lastReward - sumUtilitiesOfFeatures(currentFeatures);

            double maximumQValue = getMaxExpectedUtility(position).getUtility();

            delta += discount * maximumQValue;

            for (Feature feature : eligibility.keySet())
            {
                Double featUtility = utilities.get(feature);
                if (featUtility == null)
                    featUtility = 0D;
                Double featActivation = currentFeatures.get(feature);
                if (featActivation != null)
                    featUtility += delta * learningRate * eligibility.get(feature) * featActivation;
                utilities.put(feature, featUtility);
            }

            if (rnd.nextDouble() > epsilon)
            {
                action = getMaxExpectedUtility(position).getAction();

                for (Feature feature : eligibility.keySet())
                {
                    double featEligibility = eligibility.get(feature);
                    featEligibility *= discount * lambda;
                    eligibility.put(feature, featEligibility);
                }

            } else
            {
                action = Action.randomAction();
                eligibility.clear();
            }
        }

        return totalReward;
    }

    /**
     * Sums up the utilities of a given list of features
     *
     * @param features whose utilities are to be summed
     * @return Sum of all Feature utilities
     */
    private double sumUtilitiesOfFeatures(Map<Feature, Double> features)
    {
        double result = 0;

        for (Feature feature : features.keySet())
        {
            Double utility = utilities.get(feature);
            if (utility != null)
                result += utility * features.get(feature);
        }
        return result;
    }

    /**
     * Return utility-action pair that maximizes expected utility
     * from a given position, using one-step lookahead.
     *
     * @param location Position to maximize utility from
     * @return ExpectedUtility with action and it's associated utility
     */
    public ExpectedUtility getMaxExpectedUtility(Coordinate location)
    {
        ExpectedUtility bestExpectedUtility = new ExpectedUtility(Action.UP, -Float.MAX_VALUE);

        for (Action nextAction : Action.values())
        {
            Map<Feature, Double> nextFeatures = world.getActiveFeatures(location, nextAction);
            Double qValue = sumUtilitiesOfFeatures(nextFeatures);

            if (qValue > bestExpectedUtility.getUtility())
                bestExpectedUtility = new ExpectedUtility(nextAction, qValue);
        }
        return bestExpectedUtility;
    }
}
