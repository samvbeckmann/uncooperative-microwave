package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;

import java.util.List;

/**
 * Defines a world for the q-learning problem.
 * See {@link StandardWorld} for an example
 * implementation.
 *
 * @author Sam Beckmann
 */
public interface World
{
    /**
     * Takes a coordinate, and normalizes it so that it
     * falls within the boundaries of the world.
     *
     * @param coordinate position to be normalized.
     * @return coordinate, moved to within world bounds.
     */
    Coordinate normalizeCoordinate(Coordinate coordinate);

    /**
     * Checks to see if a given coordinate is ternimal in the world.
     *
     * @param coordinate Position to be checked
     * @return True if coordinate is terminal, else False
     */
    boolean isTerminal(Coordinate coordinate);

    /**
     * Get the reward at a position.
     *
     * @param coordinate Position to get reward from
     * @return Reward at that position
     */
    double getReward(Coordinate coordinate);

    /**
     * Get the position at which the agent will begin
     * at this particular episode.
     *
     * @return Normalized coordinate where agents starts.
     */
    Coordinate getStartingPosition();

    /**
     * @return total range of x values in the world
     */
    double getXRange();

    /**
     * @return total range of y values in the world
     */
    double getYRange();

    /**
     * Returns a dummy coordinate indicating the lowest,
     * leftmost coordinate possible in the world. Used for
     * tile coding offsets.
     *
     * @return Coordinate at lower-left of world.
     */
    Coordinate getLowerLeftCoordinate();

    /**
     * Gets the Features at a given position.
     * Usually used in conjunction with a {@link FeatureSet}
     *
     * @param coordinate position to get features for
     * @return List of all features "activated" at the given position
     */
    List<Feature> getActiveFeatures(Coordinate coordinate, Action action);

    /**
     * Set the FeatureSet of the world.
     * This FeatureSet should be used with the method getFeaturesAtCoordinate.
     *
     * @param features FeatureSet this world should use
     */
    void setFeatures(FeatureSet features);
}
