package com.samvbeckmann.ai.project2;

/**
 * Created by sam on 3/22/16.
 */
public class LikelihoodWeighting implements IInferenceAlgorithm
{
    private final int numSamples;

    public LikelihoodWeighting(int numSamples)
    {
        this.numSamples = numSamples;
    }

    @Override
    public double[] query(BayesianNetwork bn, int queryVarID, BayesianEvent event)
    {
        double[] weightedVectors = new double[bn.getNumStates(queryVarID)];

        for (int i = 0; i < numSamples; i++)
        {
            EventWeightWrapper wrapper = weightedSample(bn, event);
            int result = wrapper.event.getEvidenceAtID(queryVarID);
            weightedVectors[result] += wrapper.weight;
        }

        return BayesianHelper.normalize(weightedVectors);
    }

    /**
     * Determines the weight of a given event happening.
     * Implementation of Weighted-Sample from textbook.
     *
     * @param bn Bayesian Network
     * @param event Map of variable ID's to their known states
     * @return Event from the sample, and associated weight, wrapped in an {@link EventWeightWrapper}
     */
    private EventWeightWrapper weightedSample(BayesianNetwork bn, BayesianEvent event)
    {
        EventWeightWrapper result = new EventWeightWrapper(new BayesianEvent(event), 1);
        for (int i = 0; i < bn.getNumNodes(); i++)
        {
            if (event.getEvidenceAtID(i) != null)
                result.weight *= bn.getProbGivenParents(i, event.getEvidenceAtID(i), result.event);
            else
            {
                int randomSelection = BayesianHelper.pickRandom(bn.getDistribution(i, result.event));
                result.event.addEvidence(i, randomSelection);
            }
        }
        return result;
    }

    /**
     * Tiny Wrapper for weightedSample
     */
    private class EventWeightWrapper
    {
        final BayesianEvent event;
        double weight;

        private EventWeightWrapper(BayesianEvent event, double weight)
        {
            this.event = event;
            this.weight = weight;
        }
    }
}
