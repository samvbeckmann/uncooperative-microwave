package com.samvbeckmann.ai.project2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 3/16/16.
 */
public class BayesianEvent
{
    private Map<Integer, Boolean> states;

    public BayesianEvent()
    {
        states = new HashMap<>();
    }

    public void addStateToEvent(int variable, boolean state)
    {
        states.put(variable, state);
    }

    public boolean getStateAtVariable(int variable)
    {
        return states.get(variable);
    }


}
