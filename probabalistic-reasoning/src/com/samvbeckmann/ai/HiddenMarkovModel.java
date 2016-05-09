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

    public HiddenMarkovModel(double initialProbability,
                             double[][] stateTransitions,
                             Map<String, double[][]> evidenceTransitions)
    {
        this.initialProbability = initialProbability;
        this.stateTransitions = stateTransitions;
        this.evidenceTransitions = evidenceTransitions;
        this.evidenceHistory = new HashMap<>();
    }

    /**
     * Gives this HMM evidence that happened at a timestep, for it's records.
     * Note that since an HMM only has one giant evidence variable, the string should
     * represent a unique composition of evidence.
     *
     * @param timeStep time at which this evidence occurred.
     * @param evidence identifier for this evidence.
     */
    public void addEvidence(int timeStep, String evidence)
    {
        evidenceHistory.put(timeStep, evidence);
    }

    /**
     * Performs the Fixed-Lag-Smoothing algorithm on this HMM.
     *
     * @param maxTimeStep Maximum timestep to run the algorithm to.
     * @param lagLength number of steps before timestep to lag when returning an updated
     *                  distribution.
     * @return Smoothed estimates for all time-steps up to maxTimeStep.
     */
    @Nullable
    public double[][] fixedLagSmoothing(int maxTimeStep, int lagLength)
    {
        double[][] smoothedVector = new double[maxTimeStep + 1][2];

        double[] forwardMessage = {initialProbability, 1 - initialProbability};
        double[][] backwardTransformation = {{1, 0}, {0, 1}};

        for (int timestep = 1; timestep <= maxTimeStep; timestep++)
        {
            double[][] sensorAtTimestep = evidenceTransitions.get(evidenceHistory.get(timestep));

            if (timestep > lagLength)
            {
                forwardMessage = forwardOperation(forwardMessage, sensorAtTimestep);
                double[][] laggedEvidence = evidenceTransitions.get(evidenceHistory.get(timestep - lagLength));

                double[][] intermediate = MatrixHelper.invert2x2(laggedEvidence);
                intermediate = MatrixHelper.multiply2x2(intermediate, MatrixHelper.invert2x2(stateTransitions));
                intermediate = MatrixHelper.multiply2x2(intermediate, backwardTransformation);
                intermediate = MatrixHelper.multiply2x2(intermediate, stateTransitions);
                backwardTransformation = MatrixHelper.multiply2x2(intermediate, sensorAtTimestep);
            } else
            {
                double[][] intermediate = MatrixHelper.multiply2x2(backwardTransformation, stateTransitions);
                backwardTransformation = MatrixHelper.multiply2x2(intermediate, sensorAtTimestep);
            }

            if (timestep > lagLength)
            {
                double[] ones = {1, 1};
                double[] intermediate = MatrixHelper.multiplyMatrixByVector(backwardTransformation, ones);
                double[] result = MatrixHelper.pointwiseMultiplyVectors(intermediate, forwardMessage);
                result = Normalizer.normalize(result);
                smoothedVector[timestep] = result;
            }
        }

        return smoothedVector;
    }

    public double[][] forwardBackward(int maxTimeStep)
    {
        double[][] forwardVector = new double[maxTimeStep + 1][2];
        double[][] smoothedVector = new double[maxTimeStep + 1][2];

        forwardVector[0] = new double[]{initialProbability, 1 - initialProbability};

        double[] backwardMessage = {1, 1};

        for (int i = 1; i <= maxTimeStep; i++)
        {
            forwardVector[i] = forwardOperation(forwardVector[i - 1], evidenceTransitions.get(evidenceHistory.get(i)));
        }

        for (int i = maxTimeStep; i >= 1; i--)
        {
            smoothedVector[i] = Normalizer.normalize(MatrixHelper.pointwiseMultiplyVectors(forwardVector[i], backwardMessage));
            backwardMessage = backwardOperation(backwardMessage, evidenceTransitions.get(evidenceHistory.get(i)));
        }

        return smoothedVector;
    }

    public double[][] countryDance(int maxTimeStep)
    {
        double[] forwardVector = {initialProbability, 1 - initialProbability};
        double[] backwardMessage = {1, 1};
        double[][] smoothedVector = new double[maxTimeStep + 1][2];

        for (int i = 1; i <= maxTimeStep; i++)
        {
            forwardVector = forwardOperation(forwardVector, evidenceTransitions.get(evidenceHistory.get(i)));
        }

        for (int i = maxTimeStep; i <= 1; i--)
        {
            smoothedVector[i] = Normalizer.normalize(MatrixHelper.pointwiseMultiplyVectors(forwardVector, backwardMessage));

            double[][] intermediate = MatrixHelper.invert2x2(MatrixHelper.transpose(stateTransitions));
            intermediate = MatrixHelper.multiply2x2(intermediate, MatrixHelper.invert2x2(evidenceTransitions.get(evidenceHistory.get(i))));
            forwardVector = MatrixHelper.multiplyMatrixByVector(intermediate, forwardVector);
        }

        return smoothedVector;
    }

    private double[] forwardOperation(double[] oldMessage, double[][] evidenceMatrix)
    {
        double[][] intermediate = MatrixHelper.multiply2x2(evidenceMatrix, MatrixHelper.transpose(stateTransitions));
        return Normalizer.normalize(MatrixHelper.multiplyMatrixByVector(intermediate, oldMessage));
    }

    private double[] backwardOperation(double[] oldMessage, double[][] evidenceMatrix)
    {
        double[][] intermediate = MatrixHelper.multiply2x2(evidenceMatrix, stateTransitions);
        return MatrixHelper.multiplyMatrixByVector(intermediate, oldMessage);
    }
}
