package com.samvbeckmann.ai.project1;

import java.util.List;

/**
 * A strategy is an algorithm designed to produce a policy
 * from a given world.
 * See {@link StrategyValueIteration} for an example implementation.
 *
 * @author Sam Beckmann
 */
public interface Strategy
{
    /**
     * Creates a Policy from the world.
     *
     * @param states List of all states in the world, complete with their transition maps.
     * @return A Policy determined for the given world.
     */
    Policy generatePolicy(List<State> states);
}
