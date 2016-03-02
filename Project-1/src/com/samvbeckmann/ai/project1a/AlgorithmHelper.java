package com.samvbeckmann.ai.project1a;

import com.samvbeckmann.ai.Action;
import com.samvbeckmann.ai.ExpectedUtility;

import java.util.Map;

/**
 * Helper methods for calculating expected utilities.
 *
 * @author Sam Beckmann
 */
public final class AlgorithmHelper
{
    /**
     * Generates a policy when given a utility for each state.
     * Policy is generated based on maximizing expected utility.
     *
     * @param utilityMap Mapping of states to utilities.
     * @return Policy that maximizes expected utility.
     */
    public static Policy getPolicyFromUtilities(Map<State, Double> utilityMap)
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
    public static double getMaxExpectedUtility(Map<State, Double> utilityMap, State currentState)
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
    public static Action getArgMaxExpectedUtility(Map<State, Double> utilityMap, State currentState)
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
    private static ExpectedUtility getBestExpectedUtility(Map<State, Double> utilityMap, State currentState)
    {
        ExpectedUtility currentBestExpUtil = new ExpectedUtility(Action.UP, -Double.MAX_VALUE);
        for (Action action : Action.values())
        {
            ExpectedUtility actionExpUtil = getExpectedUtility(utilityMap, currentState, action);

            if (actionExpUtil.compareTo(currentBestExpUtil) > 0)
                currentBestExpUtil = actionExpUtil;
        }
        return currentBestExpUtil;
    }

    public static ExpectedUtility getExpectedUtility(Map<State, Double> utilityMap, State currentState, Action action)
    {
        double actionUtil = 0;
        Map<State, Double> possibleFutureStates = currentState.getPossibleOutcomes(action);

        for (State nextState : possibleFutureStates.keySet()) // Sum expected utility over possibilities
        {
            assert nextState != null : String.format("State %s has a null possible outcome", currentState);
            actionUtil += possibleFutureStates.get(nextState) * utilityMap.get(nextState);

        }

        return new ExpectedUtility(action, actionUtil);
    }

    public static char getCharFromAction(Action action)
    {
        if (action == null)
            return ' ';
        switch (action)
        {
            case UP:
                return '^';
            case RIGHT:
                return '>';
            case DOWN:
                return 'v';
            case LEFT:
                return '<';
            default:
                return ' ';
        }
    }
}
