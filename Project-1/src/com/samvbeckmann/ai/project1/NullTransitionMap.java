package com.samvbeckmann.ai.project1;

import java.util.HashMap;
import java.util.Map;

/**
 * Transition map in which you can't leave this state.
 */
public class NullTransitionMap implements TransitionMap
{
    Map<State, Float> transitionMap;


    public NullTransitionMap(State state)
    {
        transitionMap = new HashMap<>();
        transitionMap.put(state, 1.0F);
    }

    @Override
    public Map<State, Float> getTransitionTableFromAction(Action action)
    {
        return transitionMap;
    }
}
