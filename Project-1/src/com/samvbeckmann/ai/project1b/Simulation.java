package com.samvbeckmann.ai.project1b;

/**
 * Created by sam on 2/16/16.
 */
public class Simulation
{
    private static final int NUM_ITERATIONS = 1000;

    public static void main(String[] args)
    {
        World world = new StandardWorld();
        TransitionModel transitionModel = new TransitionGaussian(1, 0.3, 10);

        Agent agent = new Agent(world, transitionModel, 0.95, 0.1, 0.9);

        for (int i = 1; i <= NUM_ITERATIONS; i++)
        {
            agent.performEpisode(0.05 * (1 / i));
        }
    }
}
