package com.samvbeckmann.ai.project1b;

/**
 * Extension of Standard world, where the goal is in the middle.
 *
 * @author Sam Beckmann
 */
public class MiddleGoalWorld extends StandardWorld
{
    public MiddleGoalWorld(double stepCost, double goalReward)
    {
        super(stepCost, goalReward);
    }

    @Override
    public boolean isTerminal(Coordinate coordinate)
    {
        boolean xTerminal = coordinate.getX() >= 5 && coordinate.getX() < 6;
        boolean yTerminal = coordinate.getY() >= 5 && coordinate.getY() < 6;

        return xTerminal && yTerminal;
    }
}
