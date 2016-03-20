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
 * Created by sam on 3/16/16.
 */
public final class BayesianHelper
{
    public static BayesianNetwork makeNetworkFromFile(String filename) {

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

    public static boolean[] getParentActivations(int[] parents, Map<Integer, Boolean> event)
    {
        boolean[] parentActivations = new boolean[parents.length];
        for (int i = 0; i < parents.length; i++)
            parentActivations[i] = event.get(parents[i]);
        return parentActivations;
    }

    public static double[] normalize(double[] array)
    {
        double sum = 0;

        for (double anArray : array)
            sum += anArray;

        double normalizingFactor = 1/ sum;

        for (int i = 0; i < array.length; i++)
            array[i] *= normalizingFactor;

        return array;
    }

    /**
     * Creates a list of numbers from zero up to,
     * but not including, the parameter. Useful for indexing.
     *
     * @param number Number of items to have in the list
     * @return new list of numbers up to number
     */
    public static List<Integer> getNumberedList(int number)
    {
        List<Integer> result = new ArrayList<>(number);
        for (int i = 0; i < number; i++)
            result.add(i);
        return result;
    }

}
