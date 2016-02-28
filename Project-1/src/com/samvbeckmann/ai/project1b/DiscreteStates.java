package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.Action;

import java.util.HashMap;
import java.util.Map;

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
    public Map<Feature, Double> getActiveFeatures(Coordinate coordinate, Action action)
    {
        int xCoord = (int) Math.ceil((coordinate.getX() - lowerLeft.getX()) / xStateLength);
        if (xCoord == 0) xCoord++;
        int yCoord = (int) Math.ceil((coordinate.getY() - lowerLeft.getY()) / yStateLength);
        if (yCoord == 0) yCoord++;

        Map<Feature, Double> features = new HashMap<>();
        features.put(FeatureFactory.createTwoValueFeature(xCoord, yCoord, action), 1D);
        return features;
    }
}
