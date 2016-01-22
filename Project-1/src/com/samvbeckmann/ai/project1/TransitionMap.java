package com.samvbeckmann.ai.project1;

import java.util.Map;

/**
 * Created by sam on 1/22/16.
 */
public interface TransitionMap
{
    Map<State, Float> getTransitionTableFromAction(Action action);
}
