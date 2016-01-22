package com.samvbeckmann.ai.project1;

import java.util.Map;

/**
 * Created by sam on 1/22/16.
 */
public final class AlgorithmHelper
{
    public static Policy getPolicyFromUtilities(Map<State, Float> utilityMap)
    {
        Policy buildingPolicy = new Policy();
        for(State currentState : utilityMap.keySet())
        {
            buildingPolicy.updatePolicy(currentState, getArgMaxExpectedUtility(utilityMap, currentState));
        }

        return buildingPolicy;
    }

    public static float getMaxExpectedUtility(Map<State, Float> utilityMap, State currentState)
    {
        return getBestExpectedUtility(utilityMap, currentState).getUtility();
    }

    public static Action getArgMaxExpectedUtility(Map<State, Float> utilityMap, State currentState)
    {
        return getBestExpectedUtility(utilityMap, currentState).getAction();
    }

    private static ExpectedUtility getBestExpectedUtility(Map<State, Float> utilityMap, State currentState)
    {
        ExpectedUtility currentBestExpUtil = new ExpectedUtility(Action.UP, 0);
        for (Action action : Action.values())
        {
            float actionUtil = 0;
            Map<State, Float> possibleFutureStates = currentState.getPossibleOutcomes(action);

            for (State nextState : possibleFutureStates.keySet()) // Sum expected utility over possibilities
                actionUtil += possibleFutureStates.get(nextState) * utilityMap.get(nextState);

            if (actionUtil > currentBestExpUtil.getUtility())
                currentBestExpUtil = new ExpectedUtility(action, actionUtil);
        }
        return currentBestExpUtil;
    }
}
