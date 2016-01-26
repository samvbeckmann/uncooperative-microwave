package com.samvbeckmann.ai.project1;

import java.util.Map;

/**
 * Defines a State in the world.
 * States have a reward associated with being in them,
 * A map of possible transitions out of that map,
 * and a note if the state is terminal.
 *
 * @author Sam Beckmann
 */
public class State
{
    private float reward;
    private TransitionMap transitions;
    private boolean isTerminal;
    private boolean isInitialized;

    public State(int reward, TransitionMap transitions, boolean isTerminal)
    {
        this.reward = reward;
        this.transitions = transitions;
        this.isTerminal = isTerminal;
        isInitialized = true;
    }

    public State(float reward, boolean isTerminal)
    {
        this.reward = reward;
        this.isTerminal = isTerminal;
        isInitialized = false;
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

    /**
     * Gets the transition table for taking action in this state.
     *
     * @param action Action that would be taken.
     * @return Transition Table as a result of that action.
     */
    public Map<State, Float> getPossibleOutcomes(Action action)
    {
        return transitions.getTransitionTableFromAction(action);
    }
}