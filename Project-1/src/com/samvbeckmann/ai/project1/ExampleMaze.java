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

    public ExampleMaze()
    {
        stateIDMap = new HashMap<>();
    }

    @Override
    public List<State> getStates()
    {

        stateIDMap.put("1-3", new State(-0.04F, false));
        stateIDMap.put("1-2", new State(-0.04F, false));
        stateIDMap.put("1-1", new State(-0.04F, false));
        stateIDMap.put("2-3", new State(-0.04F, false));
        stateIDMap.put("2-1", new State(-0.04F, false));
        stateIDMap.put("3-3", new State(-0.04F, false));
        stateIDMap.put("3-2", new State(-0.04F, false));
        stateIDMap.put("3-1", new State(-0.04F, false));
        stateIDMap.put("4-3", new State(1F, true));
        stateIDMap.put("4-2", new State(-1F, true));
        stateIDMap.put("4-1", new State(-0.04F, false));

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
        stateIDMap.get("4-3").setTransitions(new NullTransitionMap(stateIDMap.get("4-3")));
        stateIDMap.get("4-2").setTransitions(new NullTransitionMap(stateIDMap.get("4-2")));
        stateIDMap.get("4-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-2"),
                stateIDMap.get("4-1"),
                stateIDMap.get("4-1"),
                stateIDMap.get("3-1")));

        List<State> states = new ArrayList<>(stateIDMap.size());

        states.addAll(stateIDMap.values().stream().collect(Collectors.toList()));

        return states;
    }

    @Override
    public State getStartingState()
    {
        return stateIDMap.get("1-1");
    }
}