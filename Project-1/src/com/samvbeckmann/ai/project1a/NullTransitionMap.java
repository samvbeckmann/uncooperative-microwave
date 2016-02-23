package com.samvbeckmann.ai.project1a;

import com.samvbeckmann.ai.Action;

import java.util.HashMap;
import java.util.Map;

/**
 * Transition map in which you can't leave this state.
 */
public class NullTransitionMap implements TransitionMap
{
    private Map<State, Double> transitionMap;


    public NullTransitionMap(State state)
    {
        transitionMap = new HashMap<>();
        transitionMap.put(state, 1.0);
    }

    @Override
    public Map<State, Double> getTransitionTableFromAction(Action action)
    {
        return transitionMap;
    }
}
