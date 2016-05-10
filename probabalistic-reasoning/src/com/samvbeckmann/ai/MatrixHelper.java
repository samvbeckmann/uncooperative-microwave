package com.samvbeckmann.ai;

import com.sun.istack.internal.NotNull;

/**
 * Simple library of Matrix functions to assist with algorithms
 * for the Hidden Markov Model. Based on the idea of matrices as
 * 2D double arrays. Some of this algorithms could be optimized,
 * others made more general, but they were what I needed for this
 * particular project.
 *
 * @author Sam Beckmann
 */
public final class MatrixHelper
{
    /**
     * Makes a transposed version of the given matrix.
     * Non-destructive.
     *
     * @param matrix Matrix to be transposed.
     * @return Transposed version of matrix.
     */
    public static double[][] transpose(double[][] matrix)
    {
        if (!monogamousRows(matrix))
            return null;

        double[][] result = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                result[j][i] = matrix[i][j];

        return result;
    }

    /**
     * Finds the determinate of a 2x2 matrix.
     *
     * @param matrix Matrix to find the determinate of.
     * @return The determinate of the given 2x2 matrix.
     */
    public static double determinate2x2(double[][] matrix)
    {
        // TODO: Test if really 2x2 matrix.
        return matrix[0][0] * matrix[1][1] - (matrix[0][1] * matrix[1][0]);
    }

    /**
     * Creates an inverted version of a matrix.
     * Non-Destructive.
     *
     * @param matrix Matrix to invert.
     * @return New, inverted 2x2 matrix.
     */
    public static double[][] invert2x2(double[][] matrix)
    {
        double[][] result = new double[2][2];

        double factor = 1 / determinate2x2(matrix);

        result[0][0] = matrix[1][1] * factor;
        result[1][1] = matrix[0][0] * factor;
        result[0][1] = - matrix[0][1] * factor;
        result[1][0] = - matrix[1][0] * factor;

        return result;
    }

    /**
     * Multiplies two 2x2 matrices to make a new product matrix.
     * Non-Destructive.
     * Both matrices must be 2x2, or the method will error.
     *
     * @param first First matrix.
     * @param second Seocnd matrix.
     * @return The product of the teo 2x2 matrices.
     */
    public static double[][] multiply2x2(double[][] first, double[][] second)
    {
        double[][] result = new double[2][2];

        result[0][0] = first[0][0] * second[0][0] + first[0][1] * second[1][0];
        result[0][1] = first[0][0] * second[0][1] + first[0][1] * second[1][1];
        result[1][0] = first[1][0] * second[0][0] + first[1][1] * second[1][0];
        result[1][1] = first[1][0] * second[0][1] + first[1][1] * second[1][1];

        return result;
    }

    /**
     * Multiplies a given matrix by a given vector.
     * Non-Destructive.
     * The matrix and vector must be of the same length, or the method will error.
     *
     * @param matrix Matrix to be multiplied.
     * @param vector Vector to be multiplied
     * @return Product of multiplying the matrix by the vector.
     */
    @NotNull
    public static double[] multiplyMatrixByVector(double[][] matrix, double[] vector)
    {
        if (!monogamousRows(matrix) || matrix.length != vector.length)
            System.exit(10); // TODO: Handle with exception.

        double[] result = new double[vector.length];

        for (int i = 0; i < matrix[0].length; i++)
        {
            double sum = 0;

            for (int j = 0; j < matrix.length; j++)
                sum += matrix[i][j] * vector[j];

            result[i] = sum;
        }

        return result;
    }

    /**
     * Point-wise multiplies the two given vectors to create new vector.
     * Non-Destructive.
     * Both vectors must be of the same length, or the method will error.
     *
     * @param first First vector.
     * @param second Second vector.
     * @return Point-wise product of first and second.
     */
    @NotNull
    public static double[] pointwiseMultiplyVectors(double[] first, double[] second)
    {
        if (first.length != second.length)
            System.exit(10); // TODO: Handle with exception.

        double[] result = new double[first.length];

        for (int i = 0; i < first.length; i++)
            result[i] = first[i] * second[i];

        return result;
    }

    /**
     * Tests if all the rows in a 2D matrix are the same length.
     *
     * @param matrix Matrix to test
     * @return True if all the rows are the same length, else false.
     */
    private static boolean monogamousRows(double[][] matrix)
    {
        int rowLength = matrix[0].length;

        for (int i = 1; i < matrix.length; i++)
            if (matrix[i].length != rowLength)
                return false;

        return true;
    }

    /**
     * Tests if the given matrix is a square matrix.
     *
     * @param matrix Matrix to test.
     * @return True if the matrix is a square, else false.
     */
    private static boolean isSquare(double[][] matrix)
    {
        return monogamousRows(matrix) && matrix.length == matrix[0].length;
    }
}
