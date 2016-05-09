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
     * Initializes the particle filtering algorithm for this DBN.
     * Must be called before {@link #particleFiltering(int)} is called
     * for the first time.
     *
     * @param n Number of samples to use in Particle Filtering.
     */
    public void initParticleFiltering(int n)
    {
        particleSamples = new boolean[n];
        for (int i = 0; i < particleSamples.length; i++)
        {
            particleSamples[i] = rnd.nextDouble() < initProb;
        }
    }

    /**
     * Performs the particle filtering algorithm in the given timestep.
     * TODO: Write more documentation, take another at algorithm
     *
     * @param timeStep Timestep to perform the algorithm on.
     * @return Estimate of state probability at the given timestep.
     */
    private double particleFiltering(int timeStep)
    {
        double[] weights = new double[particleSamples.length];

        for (int i = 0; i < particleSamples.length; i++)
        {
            particleSamples[i] = getTransition(particleSamples[i]);
            weights[i] = probEvidenceFromSample(particleSamples[i], evidenceContainer.getEvidenceAtTimestep(timeStep));
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
    private double probEvidenceFromSample(boolean state, Map<String, Boolean> evidence)
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
     * @return estimate of this sample having a "positive" state (state [0])
     *         based on the ratio of reselected samples in the state.
     */
    private double weightedSampleWithReplacement(double[] weights)
    {
        double[] totalWeights = {0, 0};

        for (int i = 0; i < particleSamples.length; i++)
        {
            totalWeights[particleSamples[i] ? 0 : 1] += weights[i];
        }

        Normalizer.normalize(totalWeights);
        int numPositiveNextSamples = 0;

        for (int i = 0; i < particleSamples.length; i++)
        {
            boolean nextSamplePositive = rnd.nextDouble() < totalWeights[0];
            particleSamples[i] = nextSamplePositive;
            if (nextSamplePositive) numPositiveNextSamples++;
        }

        return ((double) numPositiveNextSamples) / particleSamples.length;
    }

}
