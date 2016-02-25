package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.Action;

import java.util.Map;

/**
 * Defines the features in the world.
 * Can get the active features at any posiiton in the world.
 * See {@link DiscreteStates} for an example implementation.
 *
 * @author Sam Beckmann
 */
public interface FeatureSet
{
    /**
     * Gets a list of the features active at a given coordinate.
     *
     * @param coordinate Position to get features from
     * @return List of active features
     */
    Map<Feature, Double> getActiveFeatures(Coordinate coordinate, Action action);
}
