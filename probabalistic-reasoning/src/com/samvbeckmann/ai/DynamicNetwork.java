package com.samvbeckmann.ai;

import com.archonlaboratories.archonlib.utility.Normalizer;

import java.util.Map;
import java.util.Random;

/**
 * Implementation of a Dynamic Bayesian Network
 */
public class DynamicNetwork
{
    public IEvidence evidenceContainer;

    private double initProb;
    private double[] transitionProbs = new double[2]; // Only supports boolean states
    private Map<String, Double[]> evidenceProbs;
    private Random rnd;

    /**
     * Array of samples for the the particle filtering algorithm.
     * Class variable since samples are persistent.
     */
    private boolean[] particleSamples;

    public DynamicNetwork(double initProb, double[] transitionProbs, Map<String, Double[]> evidenceProbs)
    {
        this.initProb = initProb;
        this.transitionProbs = transitionProbs;
        this.evidenceProbs = evidenceProbs;
        this.rnd = new Random();
        this.evidenceContainer = new EvidenceSleep();
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

    public double particleFiltering(int timeStep)
    {
        double[] weights = new double[particleSamples.length];

        for (int i = 0; i < particleSamples.length; i++)
        {
            particleSamples[i] = getTransition(particleSamples[i]);
            weights[i] = probEvidenceFromSample(particleSamples[i], timeStep);
        }

        return weightedSampleWithReplacement(weights);
    }

    private boolean getTransition(boolean prevStep)
    {
            return rnd.nextDouble() < transitionProbs[prevStep ? 0 : 1];
    }

    private double probEvidenceFromSample(boolean state, int timeStep)
    {
        Map<String, Boolean> evidenceSet = evidenceContainer.getEvidenceAtTimestep(timeStep);

        double probability = 1;

        for (String key : evidenceSet.keySet())
        {
            boolean evidence = evidenceSet.get(key);
            double evidenceProb = evidenceProbs.get(key)[state ? 0 : 1];
            if (!evidence)
                evidenceProb = 1 - evidenceProb;
            probability *= evidenceProb;
        }

        return probability;
    }

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
