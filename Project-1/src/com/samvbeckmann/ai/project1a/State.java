package com.samvbeckmann.ai.project1a;

import com.samvbeckmann.ai.Action;

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
    private double reward;
    private TransitionMap transitions;
    private boolean isTerminal;
    private boolean isInitialized;
    private String id;

    public State(String id, int reward, TransitionMap transitions, boolean isTerminal)
    {
        this.id = id;
        this.reward = reward;
        this.transitions = transitions;
        this.isTerminal = isTerminal;
        isInitialized = true;
    }

    public State(String id, double reward, boolean isTerminal)
    {
        this.id = id;
        this.reward = reward;
        this.isTerminal = isTerminal;
        isInitialized = false;
    }

    public void setTransitions(TransitionMap transitions)
    {
        this.transitions = transitions;
        isInitialized = true;
    }

    public boolean isTerminal()
    {
        return isTerminal;
    }

    public double getReward()
    {
        return reward;
    }

    /**
     * Gets the transition table for taking action in this state.
     *
     * @param action Action that would be taken.
     * @return Transition Table as a result of that action.
     */
    public Map<State, Double> getPossibleOutcomes(Action action)
    {
        Map<State, Double> transitionTable;
        try
        {
            if (!isInitialized)
                throw new Exception();
            transitionTable = transitions.getTransitionTableFromAction(action);

        } catch (Exception e)
        {
            System.err.println("State " + id + " not initialized!\n");
            transitionTable = null;
        }

        return transitionTable;
    }

    @Override
    public String toString()
    {
        return id;
    }
}