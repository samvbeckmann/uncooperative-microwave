package com.samvbeckmann.ai.project1;

import java.util.Map;

/**
 * Created by sam on 1/21/16.
 */
public class State
{
    private float reward;
    private TransitionMap transitions;
    private boolean isTerminal;

    public State(int reward, TransitionMap transitions, boolean isTerminal)
    {
        this.reward = reward;
        this.transitions = transitions;
        this.isTerminal = isTerminal;
    }

    public State(float reward, boolean isTerminal)
    {
        this.reward = reward;
        this.isTerminal = isTerminal;
    }

    public void setTransitions(TransitionMap transitions)
    {
        this.transitions = transitions;
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
        return transitions.getTransitionTableFromAction(action);
    }
}