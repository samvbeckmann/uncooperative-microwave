package com.samvbeckmann.ai.project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Runs the MDP problem.
 */
public class Runner
{
    private static final int NUM_TESTS = 1000;
    private static final float VALUE_EPSILON = 0.001F;

    public static void main(String[] args)
    {
        Policy policy = null;
        Maze maze = null;

        try
        {
            Scanner keyboardIn = new Scanner(System.in);
            System.out.print("Enter dataset: ");
            File file = new File("datasets/" + keyboardIn.next());
            System.out.println();
            BufferedReader br = new BufferedReader(new FileReader(file));

            String mazeType = br.readLine();
            String[] mazeParams = br.readLine().split(",");

            switch (mazeType)
            {
                case "exampleMaze":
                    if (mazeParams.length != 3)
                        throw new InvalidFileFormatException("Maze argument list not correct length!");
                    maze = new ExampleMaze(Float.parseFloat(mazeParams[0]),
                            Float.parseFloat(mazeParams[1]),
                            Float.parseFloat(mazeParams[2]));
                    break;
                case "wumpusWorld":
                    if (mazeParams.length != 6)
                        throw new InvalidFileFormatException("Maze argument list not correct length!");
                    maze = new WumpusWorld(Float.parseFloat(mazeParams[0]),
                            Float.parseFloat(mazeParams[1]),
                            Float.parseFloat(mazeParams[2]),
                            Float.parseFloat(mazeParams[3]),
                            Float.parseFloat(mazeParams[4]),
                            Float.parseFloat(mazeParams[5]));
                    break;
                default:
                    throw new InvalidFileFormatException("Maze type not supported!");
            }

            String strategyType = br.readLine();
            Float discount = Float.parseFloat(br.readLine());

            switch (strategyType)
            {
                case "valueIteration":
                    policy = constructAndReportPolicy(new StrategyValueIteration(discount, VALUE_EPSILON), maze);
                    break;
                case "policyIteration":
                    policy = constructAndReportPolicy(new StrategyPolicyIteration(discount), maze);
                    break;
                default:
                    throw new InvalidFileFormatException("Strategy yype not supported!");
            }

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

//        assert policy.verifyPolicy(maze.getStates()) : "Policy not valid.";

        if(maze == null || policy == null)
        {
            System.err.println("Maze or Policy not properly Initialized! Exiting...");
            System.exit(13);
        }

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

    private static Policy constructAndReportPolicy(Strategy strategy, Maze maze)
    {
        long initTime = System.currentTimeMillis();
        Policy policy = strategy.generatePolicy(maze.getStates());
        long finalTime = System.currentTimeMillis();
        long netTime = finalTime - initTime;

        System.out.printf("Constructed policy in %d milliseconds.\n\n", netTime);
        return policy;
    }
}
