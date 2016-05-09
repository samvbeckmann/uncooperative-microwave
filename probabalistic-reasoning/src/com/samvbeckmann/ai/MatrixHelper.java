package com.samvbeckmann.ai;

import com.sun.istack.internal.NotNull;

/**
 * Created by sam on 5/4/16.
 */
public final class MatrixHelper
{
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

    public static double determinate2x2(double[][] matrix)
    {
        // TODO: Test if really 2x2 matrix.
        return matrix[0][0] * matrix[1][1] - (matrix[0][1] * matrix[1][0]);
    }

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

    public static double[][] multiply2x2(double[][] first, double[][] second)
    {
        double[][] result = new double[2][2];

        result[0][0] = first[0][0] * second[0][0] + first[0][1] * second[1][0];
        result[0][1] = first[0][0] * second[0][1] + first[0][1] * second[1][1];
        result[1][0] = first[1][0] * second[0][0] + first[1][1] * second[1][0];
        result[1][1] = first[1][0] * second[0][1] + first[1][1] * second[1][1];

        return result;
    }

    @NotNull
    public static double[] multiplyMatrixByVector(double[][] matrix, double[] vector)
    {
        if (!monogamousRows(matrix) || matrix.length != vector.length)
            System.exit(10);

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

    @NotNull
    public static double[] pointwiseMultiplyVectors(double[] first, double[] second)
    {
        if (first.length != second.length)
            System.exit(10);

        double[] result = new double[first.length];

        for (int i = 0; i < first.length; i++)
            result[i] = first[i] * second[i];

        return result;
    }

    private static boolean monogamousRows(double[][] matrix)
    {
        int rowLength = matrix[0].length;

        for (int i = 1; i < matrix.length; i++)
            if (matrix[i].length != rowLength)
                return false;

        return true;
    }

    private static boolean isSquare(double[][] matrix)
    {
        return monogamousRows(matrix) && matrix.length == matrix[0].length;
    }
}
