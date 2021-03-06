package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.Action;

import java.util.*;

/**
 * Implementation of Tile Coding features for
 * continuous state spaces.
 *
 * @author Sam Beckmann
 */
public class TileCoding implements FeatureSet
{
    private final int xResolution;
    private final int yResolution;
    private final double xRange;
    private final double yRange;
    private final List<Coordinate> offsets;

    public TileCoding(World world, int numIterations, int xResolution, int yResolution)
    {
        this.xResolution = xResolution;
        this.yResolution = yResolution;
        this.xRange = world.getXRange();
        this.yRange = world.getYRange();

        offsets = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < numIterations; i++)
        {
            double xJitter = rnd.nextDouble() * (xRange / this.xResolution) * -1;
            double yJitter = rnd.nextDouble() * (yRange / this.yResolution) * -1;

            offsets.add(world.getLowerLeftCoordinate().offsetCoordinate(xJitter, yJitter));
        }
    }


    @Override
    public Map<Feature, Double> getActiveFeatures(Coordinate coordinate, Action action)
    {
        Map<Feature, Double> features = new HashMap<>(offsets.size());
        for (int i = 0; i < offsets.size(); i++)
        {
            Coordinate offset = offsets.get(i);

            int row = (int) Math.floor(coordinate.getX() - offset.getX() / (xRange / xResolution));
            int col = (int) Math.floor(coordinate.getY() - offset.getY() / (yRange / yResolution));

            features.put(FeatureFactory.createThreeValueFeature(row, col, i, action), 1D);
        }

        return features;
    }
}