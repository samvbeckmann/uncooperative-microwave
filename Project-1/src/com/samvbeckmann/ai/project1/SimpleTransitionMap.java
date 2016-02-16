package com.samvbeckmann.ai.project1;

import java.util.HashMap;
import java.util.Map;

/**
 * Transition map with 0.8 chance of going in direction of action,
 * 0.1 chance of moving in adjacent directions.
 * Simple implementation of {@link TransitionMap}.
 *
 * Note: Assumes 4 actions.
 *
 * @author Sam Beckmann
 */
public class SimpleTransitionMap implements TransitionMap
{
    protected Map<Action, Map<State, Double>> transitions;

    /**
     * Creates transition map based on bordering states.
     * If a bordering state does not exist, use current state.
     *
     * @param up State bordering above.
     * @param right State bordering to the right.
     * @param down State bordering below.
     * @param left State bordering to the left.
     */
    public SimpleTransitionMap(State up, State right, State down, State left)
    {
        transitions = new HashMap<>();
        transitions.put(Action.UP, makeSimpleTransitionTable(up, right, left));
        transitions.put(Action.RIGHT, makeSimpleTransitionTable(right, up, down));
        transitions.put(Action.DOWN, makeSimpleTransitionTable(down, left, right));
        transitions.put(Action.LEFT, makeSimpleTransitionTable(left, down, up));
    }

    /**
     * Creates transition table from primary and secondary states.
     * @param main Primary direction, 0.8 change of going towards.
     * @param firstAlternate Secondary direction, 0.1 chance of going towards.
     * @param secondAlternate Secondary direction, 0.1 chance of going towards.
     * @return Transition table, matching subsequent states with probabilities.
     */
    protected Map<State, Double> makeSimpleTransitionTable(State main, State firstAlternate, State secondAlternate)
    {
        Map<State, Double> transitionTable = new HashMap<>();

        transitionTable.put(main, 0.8);

        if (transitionTable.containsKey(firstAlternate))
            transitionTable.put(firstAlternate, 0.1 + transitionTable.get(firstAlternate));
        else
            transitionTable.put(firstAlternate, 0.1);

        if (transitionTable.containsKey(secondAlternate))
            transitionTable.put(secondAlternate, 0.1 + transitionTable.get(secondAlternate));
        else
            transitionTable.put(secondAlternate, 0.1);

        return transitionTable;
    }

    @Override
    public Map<State, Double> getTransitionTableFromAction(Action action)
    {
        return transitions.get(action);
    }
}
