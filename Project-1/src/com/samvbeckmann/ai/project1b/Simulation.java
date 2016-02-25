package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1a.AlgorithmHelper;
import com.samvbeckmann.ai.InvalidFileFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Simulates many episodes with an agent to determine
 * an accurate utility map for the state space.
 *
 * @author Sam Beckmann
 */
public class Simulation
{
    private static final int NUM_ITERATIONS = 2000;
    private static final int STATUS_INTERVAL = NUM_ITERATIONS / 10;

    public static void main(String[] args)
    {
        World world = null;
        FeatureSet featureSet;
        TransitionModel transitionModel;
        QLearner agent = null;

        try
        {
            Scanner keyboardIn = new Scanner(System.in);
            System.out.print("Enter dataset: ");
            File file = new File("datasets/" + keyboardIn.next());
            System.out.println();
            BufferedReader br = new BufferedReader(new FileReader(file));

            String worldType = br.readLine();
            String[] mazeParams = br.readLine().split(",");

            switch (worldType)
            {
                case "standard":
                    if (mazeParams.length != 2)
                        throw new InvalidFileFormatException("World argument list not correct length!");
                    world = new StandardWorld(Double.parseDouble(mazeParams[0]), Double.parseDouble(mazeParams[1]));
                    break;
                case "centerGoal":
                    if (mazeParams.length != 2)
                        throw new InvalidFileFormatException("World argument list not correct length!");
                    world = new MiddleGoalWorld(Double.parseDouble(mazeParams[0]), Double.parseDouble(mazeParams[1]));
                    break;
                case "russel":
                    if (mazeParams.length != 3)
                        throw new InvalidFileFormatException("World argument list is not correct length");
                    world = new RusselWorld(Double.parseDouble(mazeParams[0]), Double.parseDouble(mazeParams[1]),
                            Double.parseDouble(mazeParams[2]));
                    break;
                default:
                    throw new InvalidFileFormatException("World type not supported!");
            }

            String featureName = br.readLine();

            switch (featureName) // TODO: Allow for setting the options here.
            {
                case "discrete":
                    featureSet = new DiscreteStates(world, 4, 3);
                    break;
                case "tile":
                    featureSet = new TileCoding(world, 10, 10, 10);
                    break;
                case "radial":
                    featureSet = new RadialBasisFeatures(world, 10, 10, 1);
                    break;
                default:
                    throw new InvalidFileFormatException("Feature type not recognized!");
            }

            world.setFeatures(featureSet);

            String transitionName = br.readLine();

            switch (transitionName)
            {
                case "gaussian":
                    transitionModel = new TransitionGaussian(1, 0.3, 10);
                    break;
                case "discrete":
                    transitionModel = new TransitionDiscrete(.8);
                    break;
                default:
                    throw new InvalidFileFormatException("Transition Type not recognized!");
            }

            String[] agentArgs = br.readLine().split(",");
            agent = new QLearner(world, transitionModel, Double.parseDouble(agentArgs[0]),
                    Double.parseDouble(agentArgs[1]), Double.parseDouble(agentArgs[2]));

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        assert agent != null;

//        QLearner agent = new QLearner(world, transitionModel, 1, 0.9, 0.005);

        double runningUtility = 0;

        for (int i = 1; i <= NUM_ITERATIONS; i++)
        {
            runningUtility += agent.performEpisode(.1 / i);
            if (i % STATUS_INTERVAL == 0)
            {
                System.out.printf("Trial: %d || Last %d Utility: %f\n", i, STATUS_INTERVAL,
                        runningUtility / STATUS_INTERVAL);
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
