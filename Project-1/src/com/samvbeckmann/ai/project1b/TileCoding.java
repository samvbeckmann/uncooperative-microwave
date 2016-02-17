package com.samvbeckmann.ai.project1b;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sam on 2/15/16.
 */
public class TileCoding implements FeatureSet
{
    private int xResolution;
    private int yResolution;
    private double xRange;
    private double yRange;
    private List<Coordinate> offsets;

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
    public List<Feature> getFeaturesAtCoordinate(Coordinate coordinate)
    {
        List<Feature> features = new ArrayList<>(offsets.size());
        for (int i = 0; i < offsets.size(); i++)
        {
            Coordinate offset = offsets.get(i);

            int row = (int) Math.floor(coordinate.getX() - offset.getX() / (xRange / xResolution));
            int col = (int) Math.floor(coordinate.getY() - offset.getY() / (yRange / yResolution));

            features.add(FeatureFactory.createThreeValueFeature(row, col, i));
        }

        return features;
    }
}