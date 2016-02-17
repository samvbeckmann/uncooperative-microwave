package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;

/**
 * Models transitions from a given state to a subsequent state, given an action
 * See {@link TransitionGaussian} for an example implementation.
 *
 * @author Sam Beckmann
 */
public interface TransitionModel
{
    /**
     * Gets the next coordinate from the transition model.
     *
     * @param current Position before the transition
     * @param action Action to take at the given position
     * @return The next coordinate, using the actual transition model
     */
    Coordinate getNewCoordinate(Coordinate current, Action action);

    /**
     * Simulates a transition without using the actual calculation.
     * Gets an "average" next state from the transition model.
     *
     * @param current Position before the transition
     * @param action Action to take at the given position
     * @return The next average position of the next coordinate
     */
    Coordinate getAverageTransition(Coordinate current, Action action);
}
