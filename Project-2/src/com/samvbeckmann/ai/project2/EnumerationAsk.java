package com.samvbeckmann.ai.project2;

import java.util.List;

/**
 * Created by sam on 3/22/16.
 */
public class EnumerationAsk implements IInferenceAlgorithm
{
    @Override
    public double[] query(BayesianNetwork bn, int queryVarID, BayesianEvent event)
    {
        double[] qDistribution = new double[bn.getNumStates(queryVarID)];

        for (int i = 0; i < bn.getNumStates(queryVarID); i++)
        {
            BayesianEvent instanceEvent = new BayesianEvent(event);
            instanceEvent.addEvidence(queryVarID, i);
            qDistribution[i] = enumerateAll(BayesianHelper.makeNumberedList(bn.getNumNodes()), instanceEvent, bn);
        }

        return BayesianHelper.normalize(qDistribution);
    }

    /**
     * Performs recursive enumeration over variables.
     * Implementation of Enumerate-All from textbook.
     *
     * @param vars     List of not yet ued variables in the Bayesian Network
     * @param event Map of variable ID's to their current states
     * @param bn       Bayesian Network
     * @return Probability of evidence occurring.
     */
    private double enumerateAll(List<Integer> vars, BayesianEvent event, BayesianNetwork bn)
    {
        if (vars.size() == 0)
            return 1.0;

        int y = vars.get(0);

        if (event.getEvidenceAtID(y) != null)
        {
            double thisProb = bn.getProbGivenParents(y, event.getEvidenceAtID(y), event);
            return thisProb * enumerateAll(vars.subList(1, vars.size()), event, bn);
        } else
        {
            double resultProbSum = 0;
            for (int i = 0; i < bn.getNumStates(y); i++)
            {
                event.addEvidence(y, i);
                double probOfState = bn.getProbGivenParents(y, i, event);
                resultProbSum += probOfState * enumerateAll(vars.subList(1, vars.size()), event, bn);
            }
            event.removeEvidence(y);
            return resultProbSum;
        }
    }
}
