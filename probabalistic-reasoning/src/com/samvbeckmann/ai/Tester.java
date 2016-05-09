package com.samvbeckmann.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 5/8/16.
 */
public class Tester
{
    private static final String RED_EYES = "redeyes";
    private static final String DOZING = "dozing";

    private DynamicNetwork dbn;
    private HiddenMarkovModel hmm;


    public static void main(String[] args)
    {
        Tester testyMcTestface = new Tester();
        testyMcTestface.giveDBNEvidence(25);
        testyMcTestface.giveHMMEvidence(25);

        double[][] forwardBackward = testyMcTestface.hmm.forwardBackward(25);
        double[][] countryDance = testyMcTestface.hmm.countryDance(25);
        double[][] fixedLag2 = testyMcTestface.hmm.fixedLagSmoothing(25, 2);
        double[][] fixedLag3 = testyMcTestface.hmm.fixedLagSmoothing(25, 3);
        double[][] fixedLag4 = testyMcTestface.hmm.fixedLagSmoothing(25, 4);
        double[][] fixedLag5 = testyMcTestface.hmm.fixedLagSmoothing(25, 5);

        System.out.println(testyMcTestface.copyableResults(25, forwardBackward, countryDance, fixedLag2, fixedLag3, fixedLag4, fixedLag5));


//        System.out.println("Forward-Backward Results:\n");
//        System.out.println(testyMcTestface.formatResults(testyMcTestface.hmm.forwardBackward(25)));
//
//        System.out.println("Country Dance Results:\n");
//        System.out.println(testyMcTestface.formatResults(testyMcTestface.hmm.countryDance(25)));
//
//        System.out.println("Fixed Lag Smoothing (Lag = 2) Results:\n");
//        System.out.println(testyMcTestface.formatResults(testyMcTestface.hmm.fixedLagSmoothing(25, 2)));
//
//        System.out.println("Fixed Lag Smoothing (Lag = 3) Results:\n");
//        System.out.println(testyMcTestface.formatResults(testyMcTestface.hmm.fixedLagSmoothing(25, 3)));
//
//        System.out.println("Fixed Lag Smoothing (Lag = 4) Results:\n");
//        System.out.println(testyMcTestface.formatResults(testyMcTestface.hmm.fixedLagSmoothing(25, 4)));
//
//        System.out.println("Fixed Lag Smoothing (Lag = 5) Results:\n");
//        System.out.println(testyMcTestface.formatResults(testyMcTestface.hmm.fixedLagSmoothing(25, 5)));
    }

    public Tester()
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

    private void giveDBNEvidence(int timesteps)
    {
        for (int i = 1; i <= timesteps; i++)
            dbn.evidenceContainer.addEvidence(generateDBNEvidence(i), i);
    }

    private void giveHMMEvidence(int timesteps)
    {
        for (int i = 1; i <= timesteps; i++)
            hmm.addEvidence(i, generateHMMEvidence(i));
    }

    private String formatResults(double[][] smoothedVector)
    {
        String result = "";
        for (int i = 1; i < smoothedVector.length; i++)
            result += String.format("TIMESTEP %d: %f\n", i, smoothedVector[i][0]);

        return result;
    }

    private String copyableResults( int maxValue, double[][]... smoothedVectors)
    {
        String result = "";

        for (int i = 1; i <= maxValue; i++)
        {
            for (double[][] vector : smoothedVectors)
                result += String.format("%3f\t", vector[i][0]);
            result += "\n";
        }

        return result;
    }
}
