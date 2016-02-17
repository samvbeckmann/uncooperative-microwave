package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.AlgorithmHelper;

/**
 * Created by sam on 2/16/16.
 */
public class Simulation
{
    private static final int NUM_ITERATIONS = 1000;

    public static void main(String[] args)
    {
        World world = new StandardWorld();
        FeatureSet features = new TileCoding(world, 10, 10, 10);
        world.setFeatures(features);
        TransitionModel transitionModel = new TransitionGaussian(1, 0.3, 10);

        Agent agent = new Agent(world, transitionModel, 0.95, 0.1, 0.9);

        double runningUtility = 0;

        for (int i = 1; i <= NUM_ITERATIONS; i++)
        {
            runningUtility += agent.performEpisode(0.05 * (1 / i));
            if (i % 10 == 0)
            {
                System.out.println("Trial: " + i + " Last 10 Utility: " + runningUtility / 10);
            }
            runningUtility = 0;
        }

        String displayResults = "";

        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                Coordinate position = new Coordinate((world.getXRange() / 50) * i, (world.getYRange() / 50) * j);
                displayResults += AlgorithmHelper.getCharFromAction(agent.getMaxExpectedUtility(position, true).getAction());
            }

            displayResults += "\n";
        }

        System.out.print(displayResults);
    }
}
