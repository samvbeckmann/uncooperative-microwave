package com.samvbeckmann.ai;

import com.archonlaboratories.archonlib.utility.Normalizer;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a Hidden Markov Model and associated operations.
 *
 * Filtering and Smoothing are preformed through matrix operations.
 * This implementation is hard-coded to only support two states,
 * this could be expanded in the future for any discrete number of states.
 *
 * @author Sam Beckmann
 */
class HiddenMarkovModel
{
    private double initialProbability;
    private double[][] stateTransitions;
    private Map<String, double[][]> evidenceTransitions;
    private Map<Integer, double[][]> evidenceHistory;

    HiddenMarkovModel(double initialProbability,
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
    void addEvidence(int timeStep, String evidence)
    {
        evidenceHistory.put(timeStep, evidenceTransitions.get(evidence));
    }

    /**
     * Performs the Fixed-Lag-Smoothing algorithm, generating smoothed results.
     * Fixed-Lag-Smoothing performs estimated smoothing, not exact. It has the
     * advantage having a constant space complexity, both for forward messages,
     * and for evidence. By not needed to store all previous evidence, it has
     * an advantage over the country-dance algorithm. However, the results cannot
     * be smoothed as well as a result. Larger lag lengths have linearly higher
     * space requirements, but will be more accurate.
     *
     * @param maxTimeStep Maximum timestep to run the algorithm to.
     * @param lagLength number of steps before timestep to lag when returning an updated
     *                  distribution.
     * @return Smoothed estimates for all time-steps up to maxTimeStep.
     */
    double[][] fixedLagSmoothing(int maxTimeStep, int lagLength)
    {
        double[][] smoothedVector = new double[maxTimeStep + 1][2];

        double[] forwardMessage = {initialProbability, 1 - initialProbability};
        double[][] backwardTransformation = {{1, 0}, {0, 1}};

        for (int timestep = 1; timestep <= maxTimeStep; timestep++)
        {
            double[][] sensorAtTimestep = evidenceHistory.get(timestep);

            if (timestep > lagLength)
            {
                forwardMessage = forwardOperation(forwardMessage, sensorAtTimestep);
                double[][] laggedEvidence = evidenceHistory.get(timestep - lagLength);

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

    /**
     * Performs the Forward-Backward algorithm, generating smoothed results.
     * Forward-Backward calculates all of the forward messages, then produces
     * smoothed results as it moves a single backward message backwards through
     * the timesteps. The space complexity of the algorithm is dependant on the
     * length of the chain being calculated.
     *
     * @param maxTimeStep Max time step to go up to when running this algorithm.
     * @return Vector of smoothed probability distributions.
     */
    double[][] forwardBackward(int maxTimeStep)
    {
        double[][] forwardVector = new double[maxTimeStep + 1][2];
        double[][] smoothedVector = new double[maxTimeStep + 1][2];

        forwardVector[0] = new double[]{initialProbability, 1 - initialProbability};

        double[] backwardMessage = {1, 1};

        for (int i = 1; i <= maxTimeStep; i++)
        {
            forwardVector[i] = forwardOperation(forwardVector[i - 1], evidenceHistory.get(i));
        }

        for (int i = maxTimeStep; i >= 1; i--)
        {
            smoothedVector[i] = Normalizer.normalize(MatrixHelper.pointwiseMultiplyVectors(forwardVector[i], backwardMessage));
            backwardMessage = backwardOperation(backwardMessage, evidenceHistory.get(i));
        }

        return smoothedVector;
    }

    /**
     * Performs the Country-Dance algorithm, generating smoothed results.
     * Country dance is a variation of {@link #forwardBackward(int)} that
     * uses constant space by calculating forward until the end of the sequence,
     * not saving results, then smoothing as it moves backward, using a reverse
     * forward algorithm to recalculate forward messages.
     *
     * @param maxTimeStep Max time step to go up to when performing the country dance.
     * @return Vector of smoothed probability distributions.
     */
    double[][] countryDance(int maxTimeStep)
    {
        double[] forwardVector = {initialProbability, 1 - initialProbability};
        double[] backwardMessage = {1, 1};
        double[][] smoothedVector = new double[maxTimeStep + 1][2];

        for (int i = 1; i <= maxTimeStep; i++)
            forwardVector = forwardOperation(forwardVector, evidenceHistory.get(i));

        for (int i = maxTimeStep; i >= 1; i--)
        {
            smoothedVector[i] = Normalizer.normalize(MatrixHelper.pointwiseMultiplyVectors(forwardVector, backwardMessage));

            double[][] intermediate = MatrixHelper.invert2x2(MatrixHelper.transpose(stateTransitions));
            intermediate = MatrixHelper.multiply2x2(intermediate, MatrixHelper.invert2x2(evidenceHistory.get(i)));
            forwardVector = MatrixHelper.multiplyMatrixByVector(intermediate, forwardVector);
        }

        return smoothedVector;
    }

    /**
     * Performs the Forward calculation for the Hidden Markov Model.
     * Recursive algorithm complexity mitigated by using dynamic programming,
     * Previous result of forward must be passed here.
     *
     * @param oldMessage Previous result of forward.
     * @param evidenceMatrix Evidence at the next timestep.
     * @return Updated forward message based on the new evidence.
     */
    private double[] forwardOperation(double[] oldMessage, double[][] evidenceMatrix)
    {
        double[][] intermediate = MatrixHelper.multiply2x2(evidenceMatrix, MatrixHelper.transpose(stateTransitions));
        return Normalizer.normalize(MatrixHelper.multiplyMatrixByVector(intermediate, oldMessage));
    }

    /**
     * Performs the Backward calculation for the Hidden Markov Model.
     * Recursive algorithm complexity mitigated by using dynamic programming.
     * Previous result of backward must be passed here.
     *
     * @param oldMessage Previous result of backward. (From one timestep forward)
     * @param evidenceMatrix Evidence at the previous timestep.
     * @return Updated backward message passed on the previous evidence.
     */
    private double[] backwardOperation(double[] oldMessage, double[][] evidenceMatrix)
    {
        double[][] intermediate = MatrixHelper.multiply2x2(evidenceMatrix, stateTransitions);
        return MatrixHelper.multiplyMatrixByVector(intermediate, oldMessage);
    }
}
