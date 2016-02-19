package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;

/**
 * Creates a new {@link Feature} based on
 * input integers.
 *
 * @author Sam Beckmann
 */
public class FeatureFactory
{
    /**
     * Creates and returns a new feature,
     * using an id constructed from two integers.
     *
     * @param row Row ID, or first integer coordinate
     * @param col Column ID, or second integer coordinate
     * @param action Action associated with the new Feature
     * @return New Feature with the given parameters.
     */
    public static Feature createTwoValueFeature(int row, int col, Action action)
    {
        return new Feature(String.format("%d-%d", row, col), action);
    }

    /**
     * Creates and returns a new Feature,
     * using an id constructed form three integers
     *
     * @param row Row ID, or first integer coordinate
     * @param col Column ID, or second integer coordinate
     * @param z Third integer coordinate. Tile layer in tile coding
     * @param action Action associated with the new Feature
     * @return New Feature with the given parameters.
     */
    public static Feature createThreeValueFeature(int row, int col, int z, Action action)
    {
        return new Feature(String.format("%d-%d-%d", row, col, z), action);
    }
}
