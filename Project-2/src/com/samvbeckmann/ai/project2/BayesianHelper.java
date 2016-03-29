package com.samvbeckmann.ai.project2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Helper methods for Bayesian Network Algorithms.
 *
 * @author Sam Beckmann
 */
final class BayesianHelper
{
    /**
     * Generates a Bayesian Network from a file.
     * The file should be in the /datasets directory.
     *
     * @param filename Name of file with extension
     * @return Generated Bayesian Network
     */
    static BayesianNetwork makeNetworkFromFile(String filename) {

        List<NetworkNode> nodes = new LinkedList<>();

        try {
            File file = new File("datasets/" + filename);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
            {
                line = line.trim();
                String[] stringParents = line.split(" ");
                int[] parents = new int[stringParents.length - 1];
                for (int i = 1; i < stringParents.length; i++)
                {
                    parents[i-1] = Integer.parseInt(stringParents[i]);
                }
                double[] probabilityArray = new double[1 << parents.length];

                for(int i = 0; i < 1 << parents.length; i++) {
                    String[] parts = br.readLine().trim().split(" ");
                    probabilityArray[i] = Double.parseDouble(parts[parts.length - 1]);
                }

                nodes.add(new NetworkNode(parents, probabilityArray));
            }
        } catch (IOException e)
        {
            System.err.println(e.toString());
        }

        return new BayesianNetwork(nodes.toArray(new NetworkNode[nodes.size()]));
    }

    /**
     * Returns an array of booleans of the states of parents
     * from an event.
     * The array is ordered by the order of the passed parents.
     *
     * @param parents Array of ID's of parents
     * @param event Mapping of node ID's to states
     * @return Array of parent's activations
     */
    @Deprecated
    static int[] getParentActivations(int[] parents, BayesianEvent event)
    {
        int[] parentActivations = new int[parents.length];
        for (int i = 0; i < parents.length; i++)
            parentActivations[i] = event.getEvidenceAtID(parents[i]);
        return parentActivations;
    }

    /**
     * Normalizes an array to have a sum of one.
     *
     * @param array Array of doubles to be normalized
     * @return normalized array
     */
    static double[] normalize(double[] array)
    {
        double sum = 0;

        for (double anArray : array)
            sum += anArray;

        double normalizingFactor = 1 / sum;

        for (int i = 0; i < array.length; i++)
            array[i] *= normalizingFactor;

        return array;
    }

    /**
     * Picks a random index of a normalized probability distribution,
     * proportional to the probabilities of the distribution.
     *
     * @param distribution A normalized probability distribution
     * @return An integer indicating which item in the probability distribution was chosen
     */
    public static int pickRandom(double [] distribution)
    {
        double resultingRoll = Math.random();
        int selection = 0;

        try
        {
            double runningSum = distribution[0];

            while (runningSum < resultingRoll)
                runningSum += distribution[++selection];

        } catch (IndexOutOfBoundsException indexError)
        {
            System.err.println("Distribution passed to pickRandom wasn't properly normalized!");
            System.err.println(indexError.getMessage());
            selection = 0;
        }
        return selection;
    }

    /**
     * Creates a list of numbers from zero up to,
     * but not including, the parameter. Useful for indexing.
     *
     * @param number Number of items to have in the list
     * @return new list of numbers up to number
     */
    static List<Integer> makeNumberedList(int number)
    {
        List<Integer> result = new ArrayList<>(number);
        for (int i = 0; i < number; i++)
            result.add(i);
        return result;
    }

}
