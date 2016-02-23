package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1a.AlgorithmHelper;

/**
 * Simulates many episodes with an agent to determine
 * an accurate utility map for the state space.
 *
 * @author Sam Beckmann
 */
public class Simulation
{
    private static final int NUM_ITERATIONS = 1000;
    private static final int STATUS_INTERVAL = NUM_ITERATIONS / 10;

    public static void main(String[] args)
    {
        World world = new StandardWorld();
        FeatureSet features = new TileCoding(world, 10, 10, 10);
//        FeatureSet features = new DiscreteStates(world, 10, 10);
        world.setFeatures(features);
        TransitionModel transitionModel = new TransitionGaussian(1, 0.3, 10);

        QLearner agent = new QLearner(world, transitionModel, 1, 0, 0.9, 0.005);

        double runningUtility = 0;

        for (int i = 1; i <= NUM_ITERATIONS; i++)
        {
            runningUtility += agent.performEpisode();
            if (i % STATUS_INTERVAL == 0)
            {
                System.out.printf("Trial: %d || Last %d Utility: %f\n", i, STATUS_INTERVAL, runningUtility / STATUS_INTERVAL);
                runningUtility = 0;
            }
        }

        String displayResults = "";

        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                Coordinate position = new Coordinate((world.getXRange() / 50) * i, (world.getYRange() / 50) * j);
                displayResults += AlgorithmHelper.getCharFromAction(agent.getMaxExpectedUtility(position).getAction());
            }

            displayResults += "\n";
        }

        System.out.print(displayResults);
    }
}
