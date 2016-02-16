package com.samvbeckmann.ai.project1b;

import java.util.ArrayList;
import java.util.List;

/**
 * Feature set for a discrete state space.
 * Each location in the world has precisely one feature.
 *
 * @author Sam Beckmann
 */
public class DiscreteStates implements FeatureSet
{
    private double xStateLength;
    private double yStateLength;
    private Coordinate lowerLeft;

    public DiscreteStates(World world, int xResolution, int yResolution)
    {
        this.xStateLength = xResolution / world.getXRange();
        this.yStateLength = yResolution / world.getYRange();
        this.lowerLeft = world.getLowerLeftCoordinate();
    }

    @Override
    public List<Feature> getFeaturesAtCoordinate(Coordinate coordinate)
    {
        int xCoord = (int) Math.ceil((coordinate.getX() - lowerLeft.getX()) / xStateLength);
        int yCoord = (int) Math.ceil((coordinate.getY() - lowerLeft.getY()) / yStateLength);

        List<Feature> features = new ArrayList<>();
        features.add(FeatureFactory.createTwoValueFeature(xCoord, yCoord));
        return features;
    }
}
