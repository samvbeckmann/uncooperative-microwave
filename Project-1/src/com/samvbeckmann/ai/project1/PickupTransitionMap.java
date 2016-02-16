package com.samvbeckmann.ai.project1;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a pickup transition.
 * When moving up in this state, pickup action is simulated.
 */
public class PickupTransitionMap extends SimpleTransitionMap
{
    /**
     * Creates transition map based on bordering states.
     * If a bordering state does not exist, use current state.
     *
     * @param up     State bordering above. Should be same as state in.
     * @param right  State bordering to the right.
     * @param down   State bordering below.
     * @param left   State bordering to the left.
     * @param pickup State moved to when moved "up" -> pickup action.
     */
    public PickupTransitionMap(State up, State right, State down, State left, State pickup)
    {
        super(up, right, down, left);
        transitions.put(Action.UP, makePickupTransition(pickup));

    }

    /**
     * Adds a pickup transition, which is a guaranteed transition.
     *
     * @param nextState state to transition to.
     * @return Transition map of 100% chance of one state.
     */
    private Map<State, Double> makePickupTransition(State nextState)
    {
        Map<State, Double> transitionTable = new HashMap<>();
        transitionTable.put(nextState, 1.0);
        return transitionTable;
    }
}
