package com.samvbeckmann.ai.project1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 1/22/16.
 */
public class Policy
{
    private Map<State, Action> actionMap;

    public Policy(Map<State, Action> actionMap)
    {
        this.actionMap = actionMap;
    }

    public Policy()
    {
        this(new HashMap<State, Action>());
    }

    Action getActionAtState(State state)
    {
        return actionMap.get(state);
    }

    void updatePolicy(State state, Action action)
    {
        actionMap.put(state, action);
    }

    boolean verifyPolicy(List<State> states)
    {
        for (State state : states)
            if (actionMap.get(state) == null)
                return false;

        return true;
    }
}
