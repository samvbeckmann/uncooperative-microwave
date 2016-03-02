package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.Action;

import java.util.Random;

/**
 * Discrete space TransitionModel, with probabilistic transitions.
 *
 * @author Sam Beckmann
 */
public class TransitionDiscrete implements TransitionModel
{
    private final double mainProbability;
    private final double sideProbability;
    private final Random rnd = new Random();

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
            return getCoordinateFromAction(current, Action.transformClockwise(action));
        else
            return getCoordinateFromAction(current, Action.transformCounterclockwise(action));
    }

    /**
     * Transforms a coordinate and a direction into the next possible state.
     *
     * @param current Position before transformation
     * @param action Direction in which to move
     * @return Position after moving current in action's direction
     */
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
