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