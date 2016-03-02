package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.Action;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a {@link FeatureSet} using radial basis features.
 * Each feature is activated to a degree, based on distance
 * from the current coordinate to the feature's center.
 * Falloff is determined by the activation function.
 *
 * @author Sam Beckmann
 */
public class RadialBasisFeatures implements FeatureSet
{
    /**
     * Array of the center of the features
     */
    private Coordinate[][] featureLocations;

    /**
     * Transformation on the standard deviation of the features.
     * Used to calculate the activation function.
     */
    private double adjustedFeatureWidth;

    public RadialBasisFeatures(World world, int xResolution, int yResolution, double featureWidth)
    {
        this.adjustedFeatureWidth = 2 * Math.pow(featureWidth, 2);

        featureLocations = new Coordinate[xResolution][yResolution];
        double xSepDistance = (world.getXRange() / xResolution) / 2;
        double ySepDistance = (world.getYRange() / yResolution) / 2;

        for (int x = 0; x < xResolution; x++)
        {
            for (int y = 0; y < yResolution; y++)
            {
                Coordinate location = new Coordinate(x + xSepDistance, y + ySepDistance);
                featureLocations[x][y] = location;
            }
        }
    }

    @Override
    public Map<Feature, Double> getActiveFeatures(Coordinate coordinate, Action action)
    {
        Map<Feature, Double> featureActivationMap = new HashMap<>();

        for (int x = 0; x < featureLocations.length; x++)
        {
            for (int y = 0; y < featureLocations[x].length; y++)
            {
                Feature featAtLocation = FeatureFactory.createTwoValueFeature(x, y, action);
                double activationValue = activationFunction(coordinate.getDistance(featureLocations[x][y]));
                featureActivationMap.put(featAtLocation, activationValue);
            }
        }

        return featureActivationMap;
    }

    private double activationFunction(double distance) // TODO
    {
        return Math.exp(- (Math.pow(distance, 2) / adjustedFeatureWidth));
    }
}
