package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.Action;

import java.util.Map;

/**
 * Standard implementation of {@link StandardWorld}.
 * 10x10 world starting in lower left and terminating
 * in the upper right.
 * Extend this example or make your own.
 *
 * @author Sam Beckmann
 */
public class StandardWorld implements World
{
    private FeatureSet features;
    private final double stepCost;
    private final double goalReward;

    public StandardWorld(double stepCost, double goalReward)
    {
        this.stepCost = stepCost;
        this.goalReward = goalReward;
    }

    private final Coordinate LOWER_LEFT_COORD = new Coordinate(0, 0);

    @Override
    public void setFeatures(FeatureSet features)
    {
        this.features = features;
    }

    @Override
    public Coordinate normalizeCoordinate(Coordinate coordinate)
    {
        double adjustedX = Math.min(coordinate.getX(), 10);
        adjustedX = Math.max(adjustedX, 0);
        coordinate.setX(adjustedX);

        double adjustedY = Math.min(coordinate.getY(), 10);
        adjustedY = Math.max(adjustedY, 0);
        coordinate.setY(adjustedY);

        return coordinate;
    }

    @Override
    public boolean isTerminal(Coordinate coordinate)
    {
        boolean xTerminal = coordinate.getX() > 9 && coordinate.getX() <= 10;
        boolean yTerminal = coordinate.getY() > 9 && coordinate.getY() <= 10;

        return xTerminal && yTerminal;
    }

    @Override
    public double getReward(Coordinate coordinate)
    {
        return isTerminal(coordinate) ? goalReward : stepCost;
    } // TODO: Allow tuning

    @Override
    public Coordinate getStartingPosition()
    {
        return LOWER_LEFT_COORD;
    }

    @Override
    public double getXRange()
    {
        return 10;
    }

    @Override
    public double getYRange()
    {
        return 10;
    }

    @Override
    public Coordinate getLowerLeftCoordinate()
    {
        return LOWER_LEFT_COORD;
    }

    @Override
    public Map<Feature, Double> getActiveFeatures(Coordinate coordinate, Action action)
    {
        return features.getActiveFeatures(coordinate, action);
    }
}
