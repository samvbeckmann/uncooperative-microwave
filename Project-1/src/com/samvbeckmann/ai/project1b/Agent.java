package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;
import com.samvbeckmann.ai.project1.ExpectedUtility;

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
public class Agent
{
    private Map<Feature, Double> utilities;
    private World world;
    private TransitionModel transitions;
    private double discount;
    private double epsilon;
    private double lambda;
    private Random rnd = new Random();

    public Agent(World world, TransitionModel transitions, double discount, double epsilon, double lambda)
    {
        this.world = world;
        this.transitions = transitions;
        this.discount = discount;
        this.epsilon = epsilon;
        this.lambda = lambda;
        utilities = new HashMap<>();
    }

    public double performEpisode(double learningRate)
    {
        double totalReward = 0;
        Coordinate position = world.getStartingPosition();
        Action action = Action.randomAction();
        Map<Feature, Double> eligibility = new HashMap<>();

        while (!world.isTerminal(position))
        {
            List<Feature> currentFeatures = world.getFeaturesAtCoordinate(position);
            for (Feature feature : currentFeatures)
            {
                Double eValue = eligibility.get(feature);
                if (eValue == null)
                    eValue = 0D;
                eligibility.put(feature, eValue + 1);
            }

            position = world.normalizeCoordinate(transitions.getNewCoordinate(position, action));
            double lastReward = world.getReward(position);
            totalReward += lastReward;

            double delta = lastReward - sumUtilitiesOfFeatures(currentFeatures);

            double maximumQValue = getMaxExpectedUtility(position, true).getUtility();

            delta += discount * maximumQValue;

            for (Feature feature : eligibility.keySet())
            {
                Double featUtility = utilities.get(feature);
                if (featUtility == null)
                    featUtility = 0D;
                featUtility += learningRate * delta * eligibility.get(feature);
                utilities.put(feature, featUtility);
            }

            if (rnd.nextDouble() > epsilon)
            {
                action = getMaxExpectedUtility(position, true).getAction();

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

    private double sumUtilitiesOfFeatures(List<Feature> features)
    {
        double result = 0;

        for (Feature feature : features)
        {
            Double utility = utilities.get(feature);
            if (utility != null)
                result += utility;
        }
        return result;
    }

    public ExpectedUtility getMaxExpectedUtility(Coordinate location, boolean simulateMovement)
    {
        ExpectedUtility bestExpectedUtility = new ExpectedUtility(Action.UP, -Float.MAX_VALUE);

        for (Action nextAction : Action.values())
        {
            Coordinate nextLocation;
            if (simulateMovement)
                nextLocation = transitions.getAverageTransition(location, nextAction);
            else
                nextLocation = transitions.getNewCoordinate(location, nextAction);

            nextLocation = world.normalizeCoordinate(nextLocation);

            List<Feature> nextFeatures = world.getFeaturesAtCoordinate(nextLocation);
            Double qValue = sumUtilitiesOfFeatures(nextFeatures);

            if (qValue > bestExpectedUtility.getUtility())
                bestExpectedUtility = new ExpectedUtility(nextAction, qValue);
        }
        return bestExpectedUtility;
    }
}
