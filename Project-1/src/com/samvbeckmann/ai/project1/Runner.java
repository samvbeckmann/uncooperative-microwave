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
        Maze maze = null;
        Policy policyValueIteration = null;
        Policy policyPolicyIteration = null;

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

            Float discount = Float.parseFloat(br.readLine());

            policyValueIteration = constructAndReportPolicy(new StrategyValueIteration(discount, VALUE_EPSILON), maze);
            policyPolicyIteration = constructAndReportPolicy(new StrategyPolicyIteration(discount), maze);
            System.out.println();

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        if(maze == null)
        {
            System.err.println("Maze not properly Initialized! Exiting...");
            System.exit(13);
        }

        String policyIterationViz = maze.visualizePolicy(policyPolicyIteration);
        String valueIterationViz = maze.visualizePolicy(policyValueIteration);

        if(!policyIterationViz.equals(valueIterationViz))
        {
            System.out.println("Warning! Policies are not equivalent.\n");
        }

        assert policyPolicyIteration != null;
        assert policyPolicyIteration.verifyPolicy(maze.getStates()) : "Policy not valid.";
        assert policyValueIteration != null;
        assert policyValueIteration.verifyPolicy(maze.getStates()) : "Policy not valid";

        System.out.println("Value Iteration Policy:\n\n" + valueIterationViz);
        System.out.println("Policy Iteration Policy:\n\n" + policyIterationViz);

        float totalRewardPolicy = 0;
        float totalRewardValue = 0;
        for (int i = 0; i < NUM_TESTS; i++)
        {
            Simulation policySimulation = new Simulation(maze.getStartingState(), policyPolicyIteration);
            Simulation valueSimulation = new Simulation(maze.getStartingState(), policyValueIteration);
            totalRewardPolicy += policySimulation.performSimulation();
            totalRewardValue += valueSimulation.performSimulation();
        }

        float netRewardPolicy = totalRewardPolicy / NUM_TESTS;
        float netRewardValue = totalRewardValue / NUM_TESTS;

        System.out.println("Average reward for Policy Iteration: " + netRewardPolicy);
        System.out.println("Average reward for Value Iteration:  " + netRewardValue);
    }

    private static Policy constructAndReportPolicy(Strategy strategy, Maze maze)
    {
        long initTime = System.currentTimeMillis();
        Policy policy = strategy.generatePolicy(maze.getStates());
        long finalTime = System.currentTimeMillis();
        long netTime = finalTime - initTime;

        // TODO: Timing incorrect.
//        System.out.printf("Constructed policy using %s in %d milliseconds.\n", strategy.getName(), netTime);
        return policy;
    }
}
