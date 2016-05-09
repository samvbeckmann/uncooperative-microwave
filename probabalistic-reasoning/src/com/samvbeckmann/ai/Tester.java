package com.samvbeckmann.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * This class tests the algorithms in {@link HiddenMarkovModel} and {@link DynamicNetwork}.
 * It uses pre-defined probabilities and evidence from an exercise in the textbook.
 * The data structures, however, are problem-agnostic
 * and support any probabilities or evidence sequence.
 *
 * @author Sam Beckmann
 */
public class Tester
{
    /* These variables keep track of evidence strings. */
    private static final String RED_EYES = "redeyes";
    private static final String DOZING = "dozing";

    /**
     * Dynamic Bayesian Network for testing.
     */
    private DynamicNetwork dbn;

    /**
     * Hidden Markov Model for testing.
     */
    private HiddenMarkovModel hmm;


    /**
     * Loads a new Tester, performs tests of algorithms, and outputs the results.
     *
     * @param args Not applicable.
     */
    public static void main(String[] args)
    {
        Tester tester = new Tester();
        tester.giveDBNEvidence(25);
        tester.giveHMMEvidence(25);

        double[][] forwardBackward = tester.hmm.forwardBackward(25);
        double[][] countryDance = tester.hmm.countryDance(25);
        double[][] fixedLag2 = tester.hmm.fixedLagSmoothing(25, 2);
        double[][] fixedLag3 = tester.hmm.fixedLagSmoothing(25, 3);
        double[][] fixedLag4 = tester.hmm.fixedLagSmoothing(25, 4);
        double[][] fixedLag5 = tester.hmm.fixedLagSmoothing(25, 5);
        // TODO: Write test for Particle Filtering algorithm.

        System.out.println(tester.formatResults(25, forwardBackward, countryDance, fixedLag2, fixedLag3, fixedLag4, fixedLag5));
    }

    /**
     * Creates a new Tester, which in turn creates a new DBN and HMM
     * using problem data.
     */
    private Tester()
    {
        double[] transitionProbabilities = {0.8, 0.3};
        Double[] redEyeProbs = {0.2, 0.7};
        Double[] dozeProbs = {0.1, 0.3};
        Map<String, Double[]> evidenceMap = new HashMap<>(2);
        evidenceMap.put(RED_EYES, redEyeProbs);
        evidenceMap.put(DOZING, dozeProbs);
        dbn = new DynamicNetwork(0.7, transitionProbabilities, evidenceMap);

        double[][] stateTransitions = {{0.8, 0.3}, {0.2, 0.7}};

        double[][] noEyesNoDoze = {{0.72, 0}, {0, 0.21}};
        double[][] eyesNoDoze = {{0.18, 0}, {0, 0.49}};
        double[][] noEyesDoze = {{.08, 0}, {0, .09}};
        double[][] eyesDoze = {{.02, 0}, {0, .21}};

        Map<String, double[][]> evidenceTables = new HashMap<>(4);
        evidenceTables.put("noeyesnodoze", noEyesNoDoze);
        evidenceTables.put("eyesnodoze", eyesNoDoze);
        evidenceTables.put("noeyesdoze", noEyesDoze);
        evidenceTables.put("eyesdoze", eyesDoze);

        hmm = new HiddenMarkovModel(0.7, stateTransitions, evidenceTables);
    }

    /**
     * Generates the appropriate evidence for the DBN at the given timestep.
     * The format of the result is catered to the DBN interface.
     * This evidence sequence was defined by the problem.
     *
     * @param timestep Time to generate evidence at.
     * @return A mapping of Evidence variables to boolean states.
     */
    private Map<String, Boolean> generateDBNEvidence(int timestep)
    {
        Map<String, Boolean> evidence = new HashMap<>(2);

        switch (timestep % 3)
        {
            case 1:
                evidence.put(RED_EYES, false);
                evidence.put(DOZING, false);
                break;
            case 2:
                evidence.put(RED_EYES, true);
                evidence.put(DOZING, false);
                break;
            case 0:
                evidence.put(RED_EYES, true);
                evidence.put(DOZING, true);
                break;
            default:
                System.exit(10);
        }

        return evidence;
    }

    /**
     * Generates the appropriate evidence for the HMM at the given timestep.
     * The format of the result is catered to the HMM interface.
     * This evidence sequence was defined by the problem.
     *
     * @param timestep Time to generate evidence at.
     * @return A unique string that identifies this evidence value.
     */
    private String generateHMMEvidence(int timestep)
    {
        switch (timestep % 3)
        {
            case 1:
                return "noeyesnodoze";
            case 2:
                return "eyesnodoze";
            case 0:
                return "eyesdoze";
            default:
                System.exit(10);
                return null;
        }
    }

    /**
     * Populates the DBN with evidence from t=1 to 1=timesteps.
     *
     * @param timesteps Number of timesteps to fill the DBN with.
     */
    private void giveDBNEvidence(int timesteps)
    {
        for (int i = 1; i <= timesteps; i++)
            dbn.evidenceContainer.addEvidence(generateDBNEvidence(i), i);
    }

    /**
     * Populates the HMM with evidence from t=1 to 1=timesteps.
     *
     * @param timesteps Number of timesteps the fill the HMM with.
     */
    private void giveHMMEvidence(int timesteps)
    {
        for (int i = 1; i <= timesteps; i++)
            hmm.addEvidence(i, generateHMMEvidence(i));
    }

    /**
     * Formats the results from various smoothing algorithms into a table
     * that can be easily copied into other applications.
     *
     * @param numTimesteps The number of timesteps to print.
     * @param smoothedVectors Various smoothed values from HMM smoothing algorithm outputs.
     * @return A formatted String with all the data organized into a table.
     */
    private String formatResults(int numTimesteps, double[][]... smoothedVectors)
    {
        String result = "";

        for (int i = 1; i <= numTimesteps; i++)
        {
            for (double[][] vector : smoothedVectors)
                result += String.format("%3f\t", vector[i][0]);
            result = result.trim();
            result += "\n";
        }

        return result;
    }
}
