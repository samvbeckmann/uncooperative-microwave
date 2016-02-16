package com.samvbeckmann.ai.project1b;

/**
 * Creates a {@link Feature} based on
 * input integers.
 *
 * @author Sam Beckmann
 */
public class FeatureFactory
{
    public static Feature createTwoValueFeature(int row, int col)
    {
        return new Feature(String.format("%d-%d", row, col));
    }

    public static Feature createThreeValueFeature(int row, int col, int z)
    {
        return new Feature(String.format("%d-%d-%d", row, col, z));
    }
}
