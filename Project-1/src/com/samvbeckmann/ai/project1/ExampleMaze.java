package com.samvbeckmann.ai.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple maze, as used in the book, to test strategies.
 * Sample implementation of {@link Maze}.
 */
public class ExampleMaze implements Maze
{
    private Map<String, State> stateIDMap;
    private double stepCost;
    private double terminationReward;
    private double terminationPunishment;

    public ExampleMaze(double stepCost, double terminationReward, double terminationPunishment)
    {
        this.stepCost = stepCost;
        this.terminationReward = terminationReward;
        this.terminationPunishment = terminationPunishment;
        stateIDMap = new HashMap<>();
    }

    @Override
    public List<State> getStates()
    {

        stateIDMap.put("1-3", new State("1-3", stepCost, false));
        stateIDMap.put("1-2", new State("1-2", stepCost, false));
        stateIDMap.put("1-1", new State("1-1", stepCost, false));
        stateIDMap.put("2-3", new State("2-3", stepCost, false));
        stateIDMap.put("2-1", new State("2-1", stepCost, false));
        stateIDMap.put("3-3", new State("3-3", stepCost, false));
        stateIDMap.put("3-2", new State("3-2", stepCost, false));
        stateIDMap.put("3-1", new State("3-1", stepCost, false));
        stateIDMap.put("4-3", new State("4-3", terminationReward, true));
        stateIDMap.put("4-2", new State("4-2", terminationPunishment, true));
        stateIDMap.put("4-1", new State("4-1", stepCost, false));
        stateIDMap.put("terminal", new State("terminal", 0, true));

        stateIDMap.get("1-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-3"),
                stateIDMap.get("2-3"),
                stateIDMap.get("1-2"),
                stateIDMap.get("1-3")));
        stateIDMap.get("1-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-3"),
                stateIDMap.get("1-2"),
                stateIDMap.get("1-1"),
                stateIDMap.get("1-2")));
        stateIDMap.get("1-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-2"),
                stateIDMap.get("2-1"),
                stateIDMap.get("1-1"),
                stateIDMap.get("1-1")));
        stateIDMap.get("2-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-3"),
                stateIDMap.get("3-3"),
                stateIDMap.get("2-3"),
                stateIDMap.get("1-3")));
        stateIDMap.get("2-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-1"),
                stateIDMap.get("3-1"),
                stateIDMap.get("2-1"),
                stateIDMap.get("1-1")));
        stateIDMap.get("3-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-3"),
                stateIDMap.get("4-3"),
                stateIDMap.get("3-2"),
                stateIDMap.get("2-3")));
        stateIDMap.get("3-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-3"),
                stateIDMap.get("4-2"),
                stateIDMap.get("3-1"),
                stateIDMap.get("3-2")));
        stateIDMap.get("3-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-2"),
                stateIDMap.get("4-1"),
                stateIDMap.get("3-1"),
                stateIDMap.get("2-1")));
        stateIDMap.get("4-3").setTransitions(new NullTransitionMap(stateIDMap.get("terminal")));
        stateIDMap.get("4-2").setTransitions(new NullTransitionMap(stateIDMap.get("terminal")));
        stateIDMap.get("4-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-2"),
                stateIDMap.get("4-1"),
                stateIDMap.get("4-1"),
                stateIDMap.get("3-1")));
        stateIDMap.get("terminal").setTransitions(new NullTransitionMap(stateIDMap.get("terminal")));

        List<State> states = new ArrayList<>(stateIDMap.size());

        states.addAll(stateIDMap.values().stream().collect(Collectors.toList()));

        return states;
    }

    @Override
    public State getStartingState()
    {
        return stateIDMap.get("1-1");
    }

    @Override
    public String visualizePolicy(Policy policy)
    {
        String str = "";
        for (int y = 3; y > 0;  y--)
        {
            str += "---------\n";
            for (int x = 1; x <= 4; x++)
            {
                str += "|" + AlgorithmHelper.getCharFromAction(policy.getActionAtState(stateIDMap.get(String.format("%d-%d", x, y))));
            }
            str += "|\n";
        }
        str += "---------\n";
        return str;
    }
}
