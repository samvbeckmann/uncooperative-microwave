package com.samvbeckmann.ai.project1;

import java.util.Map;
import java.util.Random;

/**
 * Simulates the traversal of a maze with a given policy.
 *
 * @author Sam Beckmann
 */
public class Simulation
{
    private State currentState;
    private Policy policy;
    private float netReward = 0;
    private Random rnd = new Random();

    public Simulation(State startingState, Policy policy)
    {
        this.currentState = startingState;
        this.policy = policy;
    }

    /**
     * Executes simulation, calculating reward from simulation.
     *
     * @return net reward from the simulation.
     */
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
            Map<State, Float> transitionTable = currentState.getPossibleOutcomes(policy.getActionAtState(currentState));
            for (State state : transitionTable.keySet())
                System.err.println(state + " -> " + transitionTable.get(state));

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

    /**
     * Takes a transition table, and randomly chooses an actual outcome.
     *
     * @param transition Transition table of subsequent state.
     * @return State which is the actual outcome of the transition table.
     * @throws InvalidTransitionTableException if transition table probabilities don't sum to 1.
     */
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
