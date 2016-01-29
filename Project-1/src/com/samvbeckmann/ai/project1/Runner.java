package com.samvbeckmann.ai.project1;

/**
 * Runs the MDP problem.
 */
public class Runner
{
    private static final int NUM_TESTS = 1000;

    public static void main(String[] args)
    {
//        Maze maze = new ExampleMaze(-.04F, 1, -1);
        Maze maze = new WumpusWorld(-.04F, -0.5F, -0.5F, -0.5F, -10F, 10F);

//        Policy policy = new StrategyValueIteration(0.95F, 0.001F).generatePolicy(maze.getStates());

        Policy policy = new StrategyPolicyIteration(1F).generatePolicy(maze.getStates());

//        assert policy.verifyPolicy(maze.getStates()) : "Policy not valid.";

        System.out.println(policy.toString());

        System.out.println(maze.visualizePolicy(policy));

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
