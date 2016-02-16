package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;

import java.util.Random;

/**
 * Created by sam on 2/15/16.
 */
public class TransitionDiscrete implements TransitionModel
{
    private double mainProbability;
    private double sideProbability;
    private Random rnd = new Random();

    public TransitionDiscrete(double mainProbability)
    {
        this.mainProbability = mainProbability;
        this.sideProbability = (1 - mainProbability) / 2;
    }

    @Override
    public Coordinate getNewCoordinate(Coordinate current, Action action)
    {
        double rndNum = rnd.nextDouble();

        if (rndNum <= mainProbability)
            return getCoordinateFromAction(current, action);
        else if (rndNum <= mainProbability + sideProbability)
            return getCoordinateFromAction(current, Action.transformRight(action));
        else
            return getCoordinateFromAction(current, Action.transformLeft(action));
    }

    @Override
    public Coordinate getAverageTransition(Coordinate current, Action action)
    {
        return getCoordinateFromAction(current, action);
    }

    private Coordinate getCoordinateFromAction(Coordinate current, Action action)
    {
        switch (action)
        {
            case UP:
                return current.offsetCoordinate(0, 1);
            case RIGHT:
                return current.offsetCoordinate(1, 0);
            case DOWN:
                return current.offsetCoordinate(0, -1);
            case LEFT:
                return current.offsetCoordinate(-1, 0);
            default:
                return current; // TODO: Make error
        }
    }
}
