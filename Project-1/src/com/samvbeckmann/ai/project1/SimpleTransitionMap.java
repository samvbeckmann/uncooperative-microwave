package com.samvbeckmann.ai.project1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 1/22/16.
 */
public class SimpleTransitionMap implements TransitionMap
{
    private Map<Action, Map<State, Float>> transitions;

    public SimpleTransitionMap(State up, State right, State down, State left)
    {
        transitions = new HashMap<Action, Map<State, Float>>();
        transitions.put(Action.UP, makeSimpleTransitionTable(up, right, left));
        transitions.put(Action.RIGHT, makeSimpleTransitionTable(right, up, down));
        transitions.put(Action.DOWN, makeSimpleTransitionTable(down, left, right));
        transitions.put(Action.LEFT, makeSimpleTransitionTable(left, down, up));
    }

    private Map<State, Float> makeSimpleTransitionTable(State main, State firstAlternate, State secondAlternate)
    {
        Map<State, Float> transitionTable = new HashMap<State, Float>();
        transitionTable.put(main, 0.8F);
        transitionTable.put(firstAlternate, 0.1F);
        transitionTable.put(secondAlternate, 0.1F);
        return transitionTable;
    }

    @Override
    public Map<State, Float> getTransitionTableFromAction(Action action)
    {
        return transitions.get(action);
    }
}
