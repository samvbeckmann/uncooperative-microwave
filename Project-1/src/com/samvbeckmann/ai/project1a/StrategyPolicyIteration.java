package com.samvbeckmann.ai.project1a;

import com.samvbeckmann.ai.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a policy based on the policy iteration strategy.
 */
public class StrategyPolicyIteration implements Strategy
{
    private double discount;

    public StrategyPolicyIteration(double discount)
    {
        this.discount = discount;
    }

    @Override
    public Policy generatePolicy(List<State> states)
    {
        Map<State, Double> utilities = new HashMap<>();
        Policy policy = new Policy();

        for(State state : states)
        {
            utilities.put(state, 0D);
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
            }
        } while (!unchanged);

        return policy;
    }

    @Override
    public String getName()
    {
        return "Policy Iteration";
    }

    private Map<State, Double> evaluatePolicy(Policy policy, List<State> states, Map<State, Double> prevUtilities)
    {
        Map<State, Double> utilityMap = new HashMap<>();

        for (State state: states)
        {
            double expectedUtility = AlgorithmHelper.getExpectedUtility(prevUtilities, state, policy.getActionAtState(state)).getUtility();
            double stateUtility = state.getReward() + discount * expectedUtility;
            utilityMap.put(state, stateUtility);
        }

        return utilityMap;
    }
}
