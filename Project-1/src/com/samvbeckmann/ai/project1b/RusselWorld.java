package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.Action;

import java.util.List;

/**
 * Defines the "Russel World" presented in the text.
 * 4x3 world with an obstacle in the middle,
 * both positive and negative rewards.
 *
 * @author Sam Beckmann
 */
public class RusselWorld implements World
{
    private double stepCost;
    private double negativeReward;
    private double positiveReward;
    private FeatureSet featureSet;

    public RusselWorld(double stepCost, double negativeReward, double positiveReward)
    {
        this.stepCost = stepCost;
        this.negativeReward = negativeReward;
        this.positiveReward = positiveReward;
    }

    @Override
    public Coordinate normalizeCoordinate(Coordinate coordinate)
    {
        if (coordinate.getX() > 1 && coordinate.getX() < 2 && coordinate.getY() > 1 && coordinate.getY() < 2)
            return new Coordinate(1, 1);

        if (coordinate.getX() < 0)
            coordinate.setX(0);
        else if (coordinate.getX() > 4)
            coordinate.setX(4);

        if (coordinate.getY() < 0)
            coordinate.setY(0);
        else if (coordinate.getY() > 2)
            coordinate.setY(2);

        return coordinate;
    }

    @Override
    public boolean isTerminal(Coordinate coordinate)
    {
        boolean xTerminal = coordinate.getX() > 3 && coordinate.getX() <= 4;
        boolean yTerminal = coordinate.getY() > 1 && coordinate.getY() <= 3;

        return xTerminal && yTerminal;
    }

    @Override
    public double getReward(Coordinate coordinate)
    {
        if (isTerminal(coordinate))
            return coordinate.getY() > 2 ? positiveReward : negativeReward;
        else
            return stepCost;
    }

    @Override
    public Coordinate getStartingPosition()
    {
        return new Coordinate(0.5, 0.5);
    }

    @Override
    public double getXRange()
    {
        return 4;
    }

    @Override
    public double getYRange()
    {
        return 3;
    }

    @Override
    public Coordinate getLowerLeftCoordinate()
    {
        return new Coordinate(0,0);
    }

    @Override
    public List<Feature> getActiveFeatures(Coordinate coordinate, Action action)
    {
        return featureSet.getActiveFeatures(coordinate, action);
    }

    @Override
    public void setFeatures(FeatureSet features)
    {
        this.featureSet = features;
    }
}
