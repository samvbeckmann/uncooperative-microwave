package com.samvbeckmann.ai.project1;

import java.util.Map;

/**
 * Created by sam on 1/21/16.
 */
public class State
{
    private float reward;
    private Map<Action, Map<State, Float>> transitions;
    private boolean isTerminal;

    public State(int reward, Map<Action, Map<State, Float>> transitions, boolean isTerminal)
    {
        this.reward = reward;
        this.transitions = transitions;
        this.isTerminal = isTerminal;
    }

    public boolean isTerminal()
    {
        return isTerminal;
    }

    public float getReward()
    {
        return reward;
    }

    public Map<State, Float> getPossibleOutcomes(Action action)
    {
        return transitions.get(action);
    }
}