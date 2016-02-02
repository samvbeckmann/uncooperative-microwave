package com.samvbeckmann.ai.project1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link Strategy}.
 * Uses value iteration to create a Policy for a given world.
 */
public class StrategyValueIteration implements Strategy
{
    private float discount;
    private float epsilon;

    public StrategyValueIteration(float discount, float epsilon)
    {
        this.discount = discount;
        this.epsilon = epsilon;
    }

    @Override
    public Policy generatePolicy(List<State> states)
    {
        Map<State, Float> currentUtilities = new HashMap<>();
        Map<State, Float> nextUtilities = new HashMap<>();
        float delta;

        for(State state : states)
            currentUtilities.put(state, 0F);

        do
        {
            delta = 0;
            for (State state : states)
            {
                float adjustedUtility = state.getReward() + discount * AlgorithmHelper.getMaxExpectedUtility(currentUtilities, state);
                nextUtilities.put(state, adjustedUtility);

                float stateDelta = Math.abs(adjustedUtility - currentUtilities.get(state));
                delta = Math.max(delta, stateDelta);
            }

            for (State state : nextUtilities.keySet())
                currentUtilities.put(state, nextUtilities.get(state));

        } while (delta > (epsilon * (1 - discount) / discount));


        return AlgorithmHelper.getPolicyFromUtilities(currentUtilities);
    }

    @Override
    public String getName()
    {
        return "Value Iteration";
    }

    public float getDiscount()
    {
        return discount;
    }

    public void setDiscount(float discount)
    {
        this.discount = discount;
    }

    public float getEpsilon()
    {
        return epsilon;
    }

    public void setEpsilon(float epsilon)
    {
        this.epsilon = epsilon;
    }
}
