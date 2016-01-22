package com.samvbeckmann.ai.project1;

import java.util.Map;
import java.util.Random;

/**
 * Created by sam on 1/22/16.
 */
public class Simulation
{
    private State currentState;
    private Policy policy;
    private float netReward = 0;
    Random rnd = new Random();

    public Simulation(State startingState, Policy policy)
    {
        this.currentState = startingState;
        this.policy = policy;
    }

    public float performSimulation()
    {
        try
        {
            while (!currentState.isTerminal())
            {
                State nextState = getActualOutcome(currentState.getPossibleOutcomes(policy.getActionAtState(currentState)));
                netReward += nextState.getReward();
                currentState = nextState;
            }
        } catch (InvalidTransitionTableException e)
        {
            e.printStackTrace();
            System.exit(12);
        }
        return netReward;
    }

    public Policy getPolicy()
    {
        return policy;
    }

    public void setPolicy(Policy policy)
    {
        this.policy = policy;
    }

    private State getActualOutcome(Map<State, Float> transition) throws InvalidTransitionTableException
    {
        float rollResult = rnd.nextFloat();

        for(State potentialState : transition.keySet())
        {
            rollResult -= transition.get(potentialState);
            if (rollResult <= 0)
                return potentialState;
        }
    throw new InvalidTransitionTableException();
    }
}
