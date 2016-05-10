package com.samvbeckmann.ai;

import com.archonlaboratories.archonlib.utility.Normalizer;

import java.util.Map;
import java.util.Random;

/**
 * Implementation of a Dynamic Bayesian Network.
 * This implementation only supports two possible states, and boolean evidence.
 * This could be expanded in the future to work with any number of discrete states,
 * as well as discrete evidence variables.
 *
 * @author Sam Beckmann
 */
class DynamicNetwork
{
    /**
     * Container to house evidence for the simulation.
     * Evidence is placed directly in the container, without
     * interacting with the DBN.
     */
    Evidence evidenceContainer;

    /**
     * Initial probability of being in state 0.
     */
    private double initProb;

    /**
     * Probability of transitioning to state 0, given you are in
     * state i.
     */
    private double[] transitionProbs = new double[2];

    /**
     * Mapping of evidence variables to their respective transition
     * probability tables.
     */
    private Map<String, Double[]> evidenceProbs;

    private Random rnd;

    /**
     * Array of samples for the the particle filtering algorithm.
     * Class variable since samples are persistent.
     */
    private boolean[] particleSamples;

    DynamicNetwork(double initProb, double[] transitionProbs, Map<String, Double[]> evidenceProbs)
    {
        this.initProb = initProb;
        this.transitionProbs = transitionProbs;
        this.evidenceProbs = evidenceProbs;
        this.rnd = new Random();
        this.evidenceContainer = new Evidence();
    }

    /**
     * Initializes the performs the particle filtering algorithm for this DBN.
     * Goes from timestep 1 until maxTimestep.
     *
     * @param maxTimestep Maximum timestep to calculate to.
     * @param n Number of samples to use in Particle Filtering.
     * @return A vector or probability distribution estimates over states.
     */
    public double[][] fullParticleFiltering(int maxTimestep, int n)
    {
        particleSamples = new boolean[n];
        for (int i = 0; i < particleSamples.length; i++)
            particleSamples[i] = rnd.nextDouble() < initProb;

        double[][] resultProbabilities = new double[maxTimestep + 1][2];

        for (int i = 1; i <= maxTimestep; i++)
        {
            resultProbabilities[i] = particleFiltering(i);
        }

        return resultProbabilities;
    }

    /**
     * Performs the particle filtering algorithm in the given timestep.
     * Uses transition table and evidence to generate new samples from
     * samples at the previous timestep. Ratios of the new samples
     * give probability estimates for the next timestep.
     *
     * @param timeStep Timestep to perform the algorithm on.
     * @return Estimate of state probability distribution at the given timestep.
     */
    private double[] particleFiltering(int timeStep)
    {
        double[] weights = new double[particleSamples.length];

        for (int i = 0; i < particleSamples.length; i++)
        {
            particleSamples[i] = getTransition(particleSamples[i]);
            weights[i] = probEvidenceGivenState(evidenceContainer.getEvidenceAtTimestep(timeStep), particleSamples[i]);
        }

        return weightedSampleWithReplacement(weights);
    }

    /**
     * Gets a next state based on a previous state and the transition table.
     * P(X_(t)|X_(t-1))
     *
     * @param prevStep State in the previous step.
     * @return Next state, randomly generated using probability table from previous state.
     */
    private boolean getTransition(boolean prevStep)
    {
            return rnd.nextDouble() < transitionProbs[prevStep ? 0 : 1];
    }

    /**
     * Gets the probability of the given evidence occurring, given state.
     * P(e|X)
     *
     * @param state State to use for probability calculation.
     * @param evidence Set of the evidence to calculate the probability of
     * @return Probability of evidence, given state.
     */
    private double probEvidenceGivenState(Map<String, Boolean> evidence, boolean state)
    {
        double probability = 1;

        for (String key : evidence.keySet())
        {
            boolean evidencePiece = evidence.get(key);
            double evidenceProb = evidenceProbs.get(key)[state ? 0 : 1];
            if (!evidencePiece)
                evidenceProb = 1 - evidenceProb;
            probability *= evidenceProb;
        }

        return probability;
    }

    /**
     * Updates the particleSamples based on the given weights.
     * The updates are done randomly, with probability being a
     * result of the weights from the previous time-step.
     *
     * @param weights Vector of weights for associated particleSamples.
     * @return probability distribution over states, calculated by the
     *         ratio of states in teh redistributed samples.
     */
    private double[] weightedSampleWithReplacement(double[] weights)
    {
        double[] totalWeights = {0, 0};
        double[] result = new double[2];

        for (int i = 0; i < particleSamples.length; i++)
            totalWeights[particleSamples[i] ? 0 : 1] += weights[i];

        totalWeights = Normalizer.normalize(totalWeights);
        int numPositiveNextSamples = 0;

        for (int i = 0; i < particleSamples.length; i++)
        {
            boolean nextSamplePositive = rnd.nextDouble() < totalWeights[0];
            particleSamples[i] = nextSamplePositive;
            if (nextSamplePositive) numPositiveNextSamples++;
        }

        result[0] = ((double) numPositiveNextSamples) / particleSamples.length;
        result[1] = 1 - result[0];

        return result;
    }

}
