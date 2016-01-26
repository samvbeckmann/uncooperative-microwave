package com.samvbeckmann.ai.project1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a policy based on the policy iteration strategy.
 */
public class StrategyPolicyIteration implements Strategy
{
    private float discount;

    public StrategyPolicyIteration(float discount)
    {
        this.discount = discount;
    }

    @Override
    public Policy generatePolicy(List<State> states)
    {
        Map<State, Float> utilites = new HashMap<>();
        Policy policy = new Policy();

        for(State state : states)
        {
            utilites.put(state, 0F);
            policy.updatePolicy(state, Action.randomAction());
        }

        boolean unchanged;
        do
        {
            utilites = evaluatePolicy(policy, states, utilites);
            unchanged = true;
            for (State state : states)
            {
                float maxExpectedUtility = AlgorithmHelper.getMaxExpectedUtility(utilites, state);
                float currentExpectedUtility = AlgorithmHelper.getExpectedUtility(utilites, state, policy.getActionAtState(state)).getUtility();

                if (maxExpectedUtility < currentExpectedUtility)
                {
                    policy.updatePolicy(state, AlgorithmHelper.getArgMaxExpectedUtility(utilites, state));
                    unchanged = false;
                }
            }
        } while (unchanged);


        return policy;
    }

    private Map<State, Float> evaluatePolicy(Policy policy, List<State> states, Map<State, Float> prevUtilities)
    {
        Map<State, Float> utilityMap = new HashMap<>();

        for (State state: states)
        {
            float expectedUtility = AlgorithmHelper.getExpectedUtility(prevUtilities, state, policy.getActionAtState(state)).getUtility();
            float stateUtility = state.getReward() + discount * expectedUtility;
            utilityMap.put(state, stateUtility);
        }

        return utilityMap;
    }
}
