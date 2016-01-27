package com.samvbeckmann.ai.project1;

/**
 * Runs the MDP problem.
 */
public class Runner
{
    private static final int NUM_TESTS = 1000;

    public static void main(String[] args)
    {
        Maze maze = new ExampleMaze();

        Policy policy = new StrategyValueIteration(0.7F, 0.001F).generatePolicy(maze.getStates());

//        Policy policy = new StrategyPolicyIteration(0.7F).generatePolicy(maze.getStates());
        System.out.print(policy.toString());

        float totalReward = 0;
        for (int i = 0; i < NUM_TESTS; i++)
        {
            Simulation sampleSimulation = new Simulation(maze.getStartingState(), policy);
            totalReward += sampleSimulation.performSimulation();
        }

        float netReward = totalReward / NUM_TESTS;

        System.out.println(netReward);
    }
}
