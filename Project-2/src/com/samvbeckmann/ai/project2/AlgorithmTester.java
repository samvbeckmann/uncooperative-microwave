package com.samvbeckmann.ai.project2;

import java.util.*;

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

        System.out.print("Percent of nodes to be hidden: ");
        int percent = keyboardIn.nextInt();

        if (percent < 0 || percent > 100)
            System.exit(12);

        int numHiddenVars = (int) Math.round(network.getNumNodes() * (percent * 0.01)) - 1;
        if (numHiddenVars < 0)
            numHiddenVars = 0;

        Random rnd = new Random();

        List<Integer> nodes = BayesianHelper.makeNumberedList(network.getNumNodes());

        for (int i = 0; i < numHiddenVars; i++)
            nodes.remove(rnd.nextInt(nodes.size()));

        int queryVar = nodes.remove(rnd.nextInt(nodes.size()));

        Map<Integer, Boolean> evidence = new HashMap<>();

        for (int node : nodes)
            evidence.put(node, rnd.nextBoolean());

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
