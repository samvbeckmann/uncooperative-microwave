package com.samvbeckmann.ai.project1a;

import com.samvbeckmann.ai.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines a policy, which maps an action to be taken in any possible state.
 *
 * @author Sam Beckmann
 */
public class Policy
{
    private Map<String, Action> actionMap;

    public Policy(Map<State, Action> actionMap)
    {
        this.actionMap = new HashMap<>();
        for (State state: actionMap.keySet())
            this.actionMap.put(state.toString(), actionMap.get(state));
    }

    public Policy()
    {
        this(new HashMap<>());
    }

    /**
     * Gets the action to be taken at a certain state.
     * Does not execute action, simply reports it.
     *
     * @param state State to look up action for.
     * @return Action to be taken at given state according to this policy.
     */
    public Action getActionAtState(State state)
    {
        return actionMap.get(state.toString());
    }

    public Action getActionFromID(String id)
    {
        return actionMap.get(id);
    }

    /**
     * Updates action in state, or adds state-action mapping
     * if state isn't in policy yet.
     *
     * @param state State to be be updated
     * @param action Action associated with state.
     */
    void updatePolicy(State state, Action action)
    {
        actionMap.put(state.toString(), action);
    }

    /**
     * Ensures that a policy contains an action for every possible state in the world.
     *
     * @param states List of states in the world
     * @return true iff all states are in the policy.
     */
    boolean verifyPolicy(List<State> states)
    {
        for (State state : states)
            if (actionMap.get(state.toString()) == null)
                return false;

        return true;
    }

    @Override
    public String toString()
    {
        String str = "";

        for (String state : actionMap.keySet())
            str += String .format("%s --> %s\n", state, actionMap.get(state).toString());

        return str;
    }
}
