package com.samvbeckmann.ai.project2;

/**
 * Created by sam on 3/22/16.
 */
public interface IInferenceAlgorithm
{
    double[] query(BayesianNetwork bn, int queryVarID, BayesianEvent event);
}
