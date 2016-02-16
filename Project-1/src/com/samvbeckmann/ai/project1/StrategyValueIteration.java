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
    private double discount;
    private double epsilon;

    public StrategyValueIteration(double discount, double epsilon)
    {
        this.discount = discount;
        this.epsilon = epsilon;
    }

    @Override
    public Policy generatePolicy(List<State> states)
    {
        Map<State, Double> currentUtilities = new HashMap<>();
        Map<State, Double> nextUtilities = new HashMap<>();
        double delta;

        for(State state : states)
            currentUtilities.put(state, 0D);

        do
        {
            delta = 0;
            for (State state : states)
            {
                double adjustedUtility = state.getReward() + discount * AlgorithmHelper.getMaxExpectedUtility(currentUtilities, state);
                nextUtilities.put(state, adjustedUtility);

                double stateDelta = Math.abs(adjustedUtility - currentUtilities.get(state));
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

    public double getDiscount()
    {
        return discount;
    }

    public void setDiscount(double discount)
    {
        this.discount = discount;
    }

    public double getEpsilon()
    {
        return epsilon;
    }

    public void setEpsilon(double epsilon)
    {
        this.epsilon = epsilon;
    }
}
