package com.samvbeckmann.ai.project1;

import java.util.List;

/**
 * A strategy is an algorithm designed to produce a policy
 * from a given world.
 * See {@link StrategyValueIteration} for an example implementation.
 *
 * @author Sam Beckmann
 */
public abstract class Strategy
{
    /**
     * Creates a Policy from the world parameters.
     *
     * @param states List of all states in the world, complete with their transition maps.
     * @param discount Gamma value for the world, how rewards decrease over time.
     * @param epsilon defines the accuracy to which the algorithm must converge
     * @return A Policy determined for the given world.
     */
    public abstract Policy generatePolicy(List<State> states, float discount, float epsilon);
    // TODO: make interface, remove args from generatePolicy
}
