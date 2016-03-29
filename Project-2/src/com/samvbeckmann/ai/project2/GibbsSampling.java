package com.samvbeckmann.ai.project2;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by sam on 3/25/16.
 */
public class GibbsSampling implements IInferenceAlgorithm
{
    private int numSamples;

    public GibbsSampling(int numSamples)
    {
        this.numSamples = numSamples;
    }

    @Override
    public double[] query(BayesianNetwork bn, int queryVarID, BayesianEvent event) // TODO Markov Blanket
    {
        BayesianEvent instanceEvent = new BayesianEvent(event);
        List<Integer> unknownVars = new LinkedList<>();
        double[] estimate = new double[bn.getNumStates(queryVarID)];

        for (int i = 0; i < bn.getNumNodes(); i++)
            if (instanceEvent.getEvidenceAtID(i) == null)
                unknownVars.add(i);

        for (int var : unknownVars)
            instanceEvent.addEvidence(var, BayesianHelper.pickRandom(bn.getDistribution(var, instanceEvent)));

        for (int i = 0; i < numSamples; i++)
        {
            for (int var : unknownVars)
                instanceEvent.addEvidence(var, BayesianHelper.pickRandom(bn.getMBDistribution(var, instanceEvent)));
            int valueInSample = instanceEvent.getEvidenceAtID(queryVarID);
            estimate[valueInSample] += 1;
        }

        return BayesianHelper.normalize(estimate);
    }
}
