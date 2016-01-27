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
        Map<State, Float> utilities = new HashMap<>();
        Policy policy = new Policy();

        for(State state : states)
        {
            utilities.put(state, 0F);
            policy.updatePolicy(state, Action.randomAction());
        }

        boolean unchanged;
        do
        {
            utilities = evaluatePolicy(policy, states, utilities);
            unchanged = true;
            for (State state : states)
            {
                Action maxUtilAction = AlgorithmHelper.getArgMaxExpectedUtility(utilities, state);

                if (maxUtilAction != policy.getActionAtState(state))
                {
                    policy.updatePolicy(state, maxUtilAction);
                    unchanged = false;
                }

//                float maxExpectedUtility = AlgorithmHelper.getMaxExpectedUtility(utilities, state);
//                float currentExpectedUtility = AlgorithmHelper.getExpectedUtility(utilities, state, policy.getActionAtState(state)).getUtility();
//
//                if (maxExpectedUtility > currentExpectedUtility)
//                {
//                    policy.updatePolicy(state, AlgorithmHelper.getArgMaxExpectedUtility(utilities, state));
//                    unchanged = false;
//                }
            }
        } while (!unchanged);


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
