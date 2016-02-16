package com.samvbeckmann.ai.project1;

import java.util.Map;

/**
 * A Transition Map returns a transition table for a given action.
 * Generally implemented with Map<Action, Map<State, Double>>,
 * but construction is left up the user.
 * Every state has a TransitionMap associated with it.
 *
 * See {@link SimpleTransitionMap} for an example implementation.
 *
 * @author Sam Beckmann
 */
public interface TransitionMap
{
    /**
     * Gets the transition table from taking a given action.
     *
     * @param action Action to be taken.
     * @return A transition table, ie a mapping of subsequent states to their probabilities.
     */
    Map<State, Double> getTransitionTableFromAction(Action action);
}
