package com.samvbeckmann.ai.project2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Tests the Algorithms in {@link Algorithms} on a
 * Bayesian Network supplied in a file.
 *
 * @author Sam Beckmann
 */
public class AlgorithmTester
{
    /**
     * Number of tests likelihood weighting and Gibbs Sampling
     * should preform to generate their estimates
     */
    private static final int NUM_TESTS = 1000;

    public static void main(String[] args)
    {
        Scanner keyboardIn = new Scanner(System.in);
        System.out.print("Enter bayesian network file: ");
        BayesianNetwork network = BayesianHelper.makeNetworkFromFile(keyboardIn.next());

        Map<Integer, Boolean> evidence = new HashMap<>();

        Random rnd = new Random();

        int queryVar = rnd.nextInt(network.getNumNodes());

        for (int i = 0; i < network.getNumNodes(); i++)
            if (i != queryVar)
                evidence.put(i, rnd.nextBoolean());

        long startingTime = System.currentTimeMillis();
        double[] enumAskResult = Algorithms.enumerationAsk(queryVar, evidence, network);
        long enumAskTime = System.currentTimeMillis() - startingTime;
        System.out.println("\nEnumeration Ask Results:");
        for (double value : enumAskResult)
            System.out.println("\t" + value);
        System.out.println("Time: " + enumAskTime);

        startingTime = System.currentTimeMillis();
        double[] likelihoodResult = Algorithms.likelihoodWeighting(queryVar, evidence, network, NUM_TESTS);
        long likelihoodTime = System.currentTimeMillis() - startingTime;
        System.out.println("\nLikelihood Weighting Results:");
        for (double value : likelihoodResult)
            System.out.println("\t" + value);
        System.out.println("Time: " + likelihoodTime);

        startingTime = System.currentTimeMillis();
        double[] gibbsResult = Algorithms.gibbsAsk(queryVar, evidence, network, NUM_TESTS);
        long gibbsTime = System.currentTimeMillis() - startingTime;
        System.out.println("\nGibbs Ask Results:");
        for (double value : gibbsResult)
            System.out.println("\t" + value);
        System.out.println("Time: " + gibbsTime);
    }
}
