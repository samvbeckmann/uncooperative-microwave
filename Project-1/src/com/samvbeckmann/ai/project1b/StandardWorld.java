package com.samvbeckmann.ai.project1b;

import java.util.List;

/**
 * Created by sam on 2/15/16.
 */
public class StandardWorld implements World
{
    private FeatureSet features;

    public void setFeatures(FeatureSet features)
    {
        this.features = features;
    }

    @Override
    public Coordinate normalizeCoordinate(Coordinate coordinate)
    {
        double adjustedX = Math.max(coordinate.getX(), 10);
        adjustedX = Math.min(adjustedX, 0);
        coordinate.setX(adjustedX);

        double adjustedY = Math.max(coordinate.getY(), 10);
        adjustedY = Math.min(adjustedY, 0);
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
        return isTerminal(coordinate) ? 1 : -.04;
    }

    @Override
    public Coordinate getStartingPosition()
    {
        return new Coordinate(0, 0);
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
        return new Coordinate(0, 0);
    }

    @Override
    public List<Feature> getFeaturesAtCoordinate(Coordinate coordinate)
    {
        return features.getFeaturesAtCoordinate(coordinate);
    }
}
