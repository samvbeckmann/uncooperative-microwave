package com.samvbeckmann.ai.project1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 1/22/16.
 */
public class StrategyValueIteration extends Strategy
{
    @Override
    public Policy generatePolicy(List<State> states, float discount, float epsilon)
    {
        Map<State, Float> currentUtilities = new HashMap<State, Float>();
        Map<State, Float> nextUtilities = new HashMap<State, Float>();
        float delta = 0;

        for(State state : states)
        {
            currentUtilities.put(state, 0F);
        }

        do
        {
            for (State state : states)
            {
                float adjustedUtility = state.getReward() + discount * AlgorithmHelper.getMaxExpectedUtility(currentUtilities, state);
                nextUtilities.put(state, adjustedUtility);

                float stateDelta = Math.abs(adjustedUtility - currentUtilities.get(state));
                delta = Math.max(delta, stateDelta);
            }

            currentUtilities = nextUtilities;

        } while (delta > (epsilon * (1 - discount) / discount));


        return AlgorithmHelper.getPolicyFromUtilities(currentUtilities);
    }
}
