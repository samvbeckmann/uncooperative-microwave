package com.samvbeckmann.ai.project1;

import java.util.Map;

/**
 * Helper methods for calculating expected utilities.
 *
 * @author Sam Beckmann
 */
final class AlgorithmHelper
{
    /**
     * Generates a policy when given a utility for each state.
     * Policy is generated based on maximizing expected utility.
     *
     * @param utilityMap Mapping of states to utilities.
     * @return Policy that maximizes expected utility.
     */
    public static Policy getPolicyFromUtilities(Map<State, Float> utilityMap)
    {
        Policy buildingPolicy = new Policy();
        for(State currentState : utilityMap.keySet())
        {
            buildingPolicy.updatePolicy(currentState, getArgMaxExpectedUtility(utilityMap, currentState));
        }

        return buildingPolicy;
    }

    /**
     * Gets the maximum utility that can be achieved in this state.
     *
     * @param utilityMap Mapping of states to utilities.
     * @param currentState State to calculate max utility at.
     * @return Maximum utility that can be achieved by taking any action in currentState.
     */
    public static float getMaxExpectedUtility(Map<State, Float> utilityMap, State currentState)
    {
        return getBestExpectedUtility(utilityMap, currentState).getUtility();
    }

    /**
     * Gets the argument which maximizes the expected utility when in the given state.
     *
     * @param utilityMap Mapping of states to utilities.
     * @param currentState State to calculated best action for.
     * @return Action that, when used in currentState, maximizes total expected utility.
     */
    public static Action getArgMaxExpectedUtility(Map<State, Float> utilityMap, State currentState)
    {
        return getBestExpectedUtility(utilityMap, currentState).getAction();
    }

    /**
     * Gets the best expected utility at a state.
     *
     * @param utilityMap Mapping of states to utilities.
     * @param currentState state to find best expected utility
     * @return Max ExpectedUtility, containing utility achieved and action that achieves it.
     */
    private static ExpectedUtility getBestExpectedUtility(Map<State, Float> utilityMap, State currentState)
    {
        ExpectedUtility currentBestExpUtil = new ExpectedUtility(Action.UP, 0);
        for (Action action : Action.values())
        {
            ExpectedUtility actionExpUtil = getExpectedUtility(utilityMap, currentState, action);

            if (actionExpUtil.getUtility() > currentBestExpUtil.getUtility())
                currentBestExpUtil = actionExpUtil;
        }
        return currentBestExpUtil;
    }

    public static ExpectedUtility getExpectedUtility(Map<State, Float> utilityMap, State currentState, Action action)
    {
        float actionUtil = 0;
        Map<State, Float> possibleFutureStates = currentState.getPossibleOutcomes(action);

        for (State nextState : possibleFutureStates.keySet()) // Sum expected utility over possibilities
            actionUtil += possibleFutureStates.get(nextState) * utilityMap.get(nextState);

        return new ExpectedUtility(action, actionUtil);

    }
}
