package com.samvbeckmann.ai.project2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains algorithms for Bayesian Network prediction
 *
 * @author Sam Beckmann
 */
final class Algorithms
{
    /**
     * Determines the exact probabilities of events.
     * Implementation of Enumeration-Ask from textbook.
     *
     * @param queryVar ID of variable to be queried
     * @param evidence Map of variable ID's to their known state
     * @param bn       Bayesian Network
     * @return a Probability distribution of query variable
     */
    static double[] enumerationAsk(int queryVar, Map<Integer, Boolean> evidence, BayesianNetwork bn)
    {
        double[] qDistribution = new double[2];

        Map<Integer, Boolean> trueEvidence = new HashMap<>(evidence);
        trueEvidence.put(queryVar, true);
        qDistribution[0] = enumerateAll(BayesianHelper.getNumberedList(bn.getNumNodes()), trueEvidence, bn);
        Map<Integer, Boolean> falseEvidence = new HashMap<>(evidence);
        falseEvidence.put(queryVar, false);
        qDistribution[1] = enumerateAll(BayesianHelper.getNumberedList(bn.getNumNodes()), falseEvidence, bn);

        return BayesianHelper.normalize(qDistribution);
    }

    /**
     * Performs recursive enumeration over variables.
     * Implementation of Enumerate-All from textbook.
     *
     * @param vars     List of not yet ued variables in the Bayesian Network
     * @param evidence Map of variable ID's to their current states
     * @param bn       Bayesian Network
     * @return Probability of evidence occurring.
     */
    private static double enumerateAll(List<Integer> vars, Map<Integer, Boolean> evidence, BayesianNetwork bn)
    {
        if (vars.size() == 0)
            return 1.0;
        int y = vars.remove(0);
        if (evidence.get(y) != null)
        {
            double thisProb = bn.getProbGivenParents(y, evidence.get(y),
                    BayesianHelper.getParentActivations(bn.getParents(y), evidence));
            return thisProb * enumerateAll(vars, evidence, bn);
        } else
        {
            evidence.put(y, true);
            double yTrue =
                    bn.getProbGivenParents(y, true, BayesianHelper.getParentActivations(bn.getParents(y), evidence)) *
                            enumerateAll(vars, evidence, bn);
            evidence.put(y, false);
            double yFalse =
                    bn.getProbGivenParents(y, true, BayesianHelper.getParentActivations(bn.getParents(y), evidence)) *
                            enumerateAll(vars, evidence, bn);
            evidence.remove(y);
            return yTrue + yFalse;
        }
    }

    /**
     * Estimates the probability distribution for a
     * query variable given an event.
     * Implementation of Likelihood-Weighting from textbook.
     *
     * @param queryVar ID of variable to be queried
     * @param event Map of variable ID's to their known states
     * @param bn Bayesian Network
     * @param numSamples Number of trials to perform to generate estimate
     * @return Estimate of probability distribution for query variable
     */
    static double[] likelihoodWeighting(int queryVar, Map<Integer, Boolean> event, BayesianNetwork bn, int numSamples)
    {
        double[] weightedVectors = new double[2];

        for (int i = 0; i < numSamples; i++)
        {
            Map<Integer, Boolean> sampleMap = new HashMap<>(event);
            EventWeightWrapper wrapper = weightedSample(bn, sampleMap);
            int result = wrapper.event.get(queryVar) ? 0 : 1;
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
    private static EventWeightWrapper weightedSample(BayesianNetwork bn, Map<Integer, Boolean> event)
    {
        EventWeightWrapper result = new EventWeightWrapper(event, 1);
        for (int i = 0; i < bn.getNumNodes(); i++)
        {
            if (event.get(i) != null)
                result.weight *= bn.getProbGivenParents(i, event.get(i),
                        BayesianHelper.getParentActivations(bn.getParents(i), event));
            else
                result.event.put(i, Math.random() > bn.getProbGivenParents(i, true,
                        BayesianHelper.getParentActivations(bn.getParents(i), event)));
        }
        return result;
    }

    static double[] gibbsAsk(int queryVar, Map<Integer, Boolean> event, BayesianNetwork bn, int numSamples)
    {

        double[] estimate = {0, 0};
        List<Integer> unknownVars = new LinkedList<>();
        for (int i = 0; i < bn.getNumNodes(); i++)
            if (event.get(i) == null)
                unknownVars.add(i);

        for (int i = 0; i < numSamples; i++)
        {
            for (int var : unknownVars)
                event.put(var, Math.random() > bn.getProbGivenParents(var, true,
                        BayesianHelper.getParentActivations(bn.getParents(var), event)));
            boolean valueInSample = event.get(queryVar);
            estimate[valueInSample ? 0 : 1] += 1;
            unknownVars.forEach(event::remove);
        }

        return BayesianHelper.normalize(estimate);
    }

    /**
     * Tiny Wrapper for weightedSample
     */
    private static class EventWeightWrapper
    {
        Map<Integer, Boolean> event;
        double weight;

        EventWeightWrapper(Map<Integer, Boolean> event, double weight)
        {
            this.event = event;
            this.weight = weight;
        }
    }
}
