package com.samvbeckmann.ai;

import com.archonlaboratories.archonlib.utility.Normalizer;
import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 5/4/16.
 */
public class HiddenMarkovModel
{
    private double initialProbability;
    private double[][] stateTransitions;
    private Map<String, double[][]> evidenceTransitions;
    private Map<Integer, String> evidenceHistory;

    private double[] forwardMessage;
    private double[][] backwardTransformation = {{1, 0}, {0, 1}};

    public HiddenMarkovModel(double initialProbability,
                             double[][] stateTransitions,
                             Map<String, double[][]> evidenceTransitions)
    {
        this.initialProbability = initialProbability;
        this.stateTransitions = stateTransitions;
        this.evidenceTransitions = evidenceTransitions;
        this.forwardMessage = new double[]{initialProbability, 1 - initialProbability};
        this.evidenceHistory = new HashMap<>();
    }

    public void addEvidence(int timeStep, String evidence)
    {
        evidenceHistory.put(timeStep, evidence);
    }

    @Nullable
    public double[] fixedLagSmoothing(int timestep, int lagLength)
    {
        double[][] sensorAtTimestep = evidenceTransitions.get(evidenceHistory.get(timestep));

        if (timestep > lagLength)
        {
            forwardMessage = updateMessage(forwardMessage, sensorAtTimestep);
            double[][] laggedEvidence = evidenceTransitions.get(evidenceHistory.get(timestep - lagLength));

            double[][] intermediate = MatrixHelper.invert2x2(laggedEvidence);
            intermediate = MatrixHelper.multiply2x2(intermediate, MatrixHelper.invert2x2(stateTransitions));
            intermediate = MatrixHelper.multiply2x2(intermediate, backwardTransformation);
            intermediate = MatrixHelper.multiply2x2(intermediate, stateTransitions);
            backwardTransformation = MatrixHelper.multiply2x2(intermediate, sensorAtTimestep);
        }
        else
        {
            double[][] intermediate = MatrixHelper.multiply2x2(backwardTransformation, stateTransitions);
            backwardTransformation = MatrixHelper.multiply2x2(intermediate, sensorAtTimestep);
        }

        if (timestep > lagLength)
            return Normalizer.normalize(MatrixHelper.multiplyMatrixByVector(backwardTransformation, forwardMessage));
        else
            return null;
    }

    private double[] updateMessage(double[] oldMessage, double[][] evidenceMatrix)
    {
        double[][] intermediate = MatrixHelper.multiply2x2(evidenceMatrix, MatrixHelper.transpose(stateTransitions));
        return Normalizer.normalize(MatrixHelper.multiplyMatrixByVector(intermediate, oldMessage));
    }
}
