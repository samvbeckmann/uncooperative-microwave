package com.samvbeckmann.ai.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implements the Wumpus World Maze we decided on in class.
 */
public class WumpusWorld implements Maze
{
    private Map<String, State> stateIDMap;
    private float stepCost;
    private float pit1Cost;
    private float pit2Cost;
    private float pit3Cost;
    private float wumpusCost;
    private float successCost;

    public WumpusWorld(float stepCost,
                       float pit1Cost,
                       float pit2Cost,
                       float pit3Cost,
                       float wumpusCost,
                       float successCost)
    {
        this.stepCost = stepCost;
        this.pit1Cost = pit1Cost;
        this.pit2Cost = pit2Cost;
        this.pit3Cost = pit3Cost;
        this.wumpusCost = wumpusCost;
        this.successCost = successCost;
        this.stateIDMap = new HashMap<>();
    }

    @Override
    public List<State> getStates()
    {
        stateIDMap.put("wumpus", new State(wumpusCost, true));
        stateIDMap.put("terminal", new State(successCost, true));

        stateIDMap.put("1-1-1", new State(stepCost, false));
        stateIDMap.put("1-2-1", new State(stepCost, false));
        stateIDMap.put("1-4-1", new State(stepCost, false));
        stateIDMap.put("2-1-1", new State(stepCost, false));
        stateIDMap.put("2-2-1", new State(stepCost, false));
        stateIDMap.put("2-3-1", new State(pit1Cost, false));
        stateIDMap.put("3-1-1", new State(pit3Cost, false));
        stateIDMap.put("3-2-1", new State(stepCost, false));
        stateIDMap.put("3-3-1", new State(pit2Cost, false));
        stateIDMap.put("3-4-1", new State(stepCost, false));
        stateIDMap.put("4-1-1", new State(stepCost, false));
        stateIDMap.put("4-2-1", new State(stepCost, false));
        stateIDMap.put("4-3-1", new State(stepCost, false));
        stateIDMap.put("4-4-1", new State(stepCost, false));

        stateIDMap.put("1-1-2", new State(stepCost, false));
        stateIDMap.put("1-2-2", new State(stepCost, false));
        stateIDMap.put("1-3-2", new State(stepCost, false));
        stateIDMap.put("1-4-2", new State(stepCost, false));
        stateIDMap.put("2-1-2", new State(stepCost, false));
        stateIDMap.put("2-2-2", new State(stepCost, false));
        stateIDMap.put("2-3-2", new State(pit1Cost, false));
        stateIDMap.put("2-4-2", new State(stepCost, false));
        stateIDMap.put("3-1-2", new State(pit3Cost, false));
        stateIDMap.put("3-2-2", new State(stepCost, false));
        stateIDMap.put("3-3-2", new State(pit2Cost, false));
        stateIDMap.put("3-4-2", new State(stepCost, false));
        stateIDMap.put("4-1-2", new State(stepCost, false));
        stateIDMap.put("4-2-2", new State(stepCost, false));
        stateIDMap.put("4-3-2", new State(stepCost, false));
        stateIDMap.put("4-4-2", new State(stepCost, false));

        stateIDMap.put("1-2-3", new State(stepCost, false));
        stateIDMap.put("1-4-3", new State(stepCost, false));
        stateIDMap.put("2-1-3", new State(stepCost, false));
        stateIDMap.put("2-2-3", new State(stepCost, false));
        stateIDMap.put("2-3-3", new State(pit1Cost, false));
        stateIDMap.put("3-1-3", new State(pit3Cost, false));
        stateIDMap.put("3-2-3", new State(stepCost, false));
        stateIDMap.put("3-3-3", new State(pit2Cost, false));
        stateIDMap.put("3-4-3", new State(stepCost, false));
        stateIDMap.put("4-1-3", new State(stepCost, false));
        stateIDMap.put("4-2-3", new State(stepCost, false));
        stateIDMap.put("4-3-3", new State(stepCost, false));
        stateIDMap.put("4-4-3", new State(stepCost, false));

        stateIDMap.put("1-2-4", new State(stepCost, false));
        stateIDMap.put("1-3-4", new State(stepCost, false));
        stateIDMap.put("1-4-4", new State(stepCost, false));
        stateIDMap.put("2-1-4", new State(stepCost, false));
        stateIDMap.put("2-2-4", new State(stepCost, false));
        stateIDMap.put("2-3-4", new State(pit1Cost, false));
        stateIDMap.put("2-4-4", new State(pit1Cost, false));
        stateIDMap.put("3-1-4", new State(pit3Cost, false));
        stateIDMap.put("3-2-4", new State(stepCost, false));
        stateIDMap.put("3-3-4", new State(pit2Cost, false));
        stateIDMap.put("3-4-4", new State(stepCost, false));
        stateIDMap.put("4-1-4", new State(stepCost, false));
        stateIDMap.put("4-2-4", new State(stepCost, false));
        stateIDMap.put("4-3-4", new State(stepCost, false));
        stateIDMap.put("4-4-4", new State(stepCost, false));

        stateIDMap.get("wumpus").setTransitions(new NullTransitionMap(stateIDMap.get("wumpus")));
        stateIDMap.get("terminal").setTransitions(new NullTransitionMap(stateIDMap.get("terminal")));

        /*
         Transitions for level 1
         */
        stateIDMap.get("1-1-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-2-1"), // UP
                stateIDMap.get("2-1-1"), // RIGHT
                stateIDMap.get("1-1-1"), // DOWN
                stateIDMap.get("1-1-1"))); // LEFT
        stateIDMap.get("1-2-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("wumpus"), // UP
                stateIDMap.get("2-2-1"), // RIGHT
                stateIDMap.get("1-1-1"), // DOWN
                stateIDMap.get("1-2-1"))); // LEFT
        stateIDMap.get("1-4-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-4-1"), // UP
                stateIDMap.get("2-4-2"), // RIGHT
                stateIDMap.get("wumpus"), // DOWN
                stateIDMap.get("1-4-1"))); // LEFT
        stateIDMap.get("2-1-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-2-1"), // UP
                stateIDMap.get("3-1-1"), // RIGHT
                stateIDMap.get("2-1-1"), // DOWN
                stateIDMap.get("1-1-1"))); // LEFT
        stateIDMap.get("2-2-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-3-1"), // UP
                stateIDMap.get("3-2-1"), // RIGHT
                stateIDMap.get("2-1-1"), // DOWN
                stateIDMap.get("1-2-1"))); // LEFT
        stateIDMap.get("2-3-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-4-2"), // UP
                stateIDMap.get("3-3-1"), // RIGHT
                stateIDMap.get("2-2-1"), // DOWN
                stateIDMap.get("wumpus"))); // LEFT
        stateIDMap.get("3-1-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-2-1"), // UP
                stateIDMap.get("4-1-1"), // RIGHT
                stateIDMap.get("3-1-1"), // DOWN
                stateIDMap.get("2-1-1"))); // LEFT
        stateIDMap.get("3-2-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-3-1"), // UP
                stateIDMap.get("4-2-1"), // RIGHT
                stateIDMap.get("3-1-1"), // DOWN
                stateIDMap.get("2-2-1"))); // LEFT
        stateIDMap.get("3-3-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-4-1"), // UP
                stateIDMap.get("4-3-1"), // RIGHT
                stateIDMap.get("3-2-1"), // DOWN
                stateIDMap.get("2-3-1"))); // LEFT
        stateIDMap.get("3-4-1").setTransitions(new PickupTransitionMap(
                stateIDMap.get("3-4-1"), // UP
                stateIDMap.get("4-4-1"), // RIGHT
                stateIDMap.get("3-3-1"), // DOWN
                stateIDMap.get("2-4-2"), // LEFT
                stateIDMap.get("3-4-3"))); // PICKUP
        stateIDMap.get("4-1-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-2-1"), // UP
                stateIDMap.get("4-1-1"), // RIGHT
                stateIDMap.get("4-1-1"), // DOWN
                stateIDMap.get("3-1-1"))); // LEFT
        stateIDMap.get("4-2-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-3-1"), // UP
                stateIDMap.get("4-2-1"), // RIGHT
                stateIDMap.get("4-1-1"), // DOWN
                stateIDMap.get("3-2-1"))); // LEFT
        stateIDMap.get("4-3-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-4-1"), // UP
                stateIDMap.get("4-3-1"), // RIGHT
                stateIDMap.get("4-2-1"), // DOWN
                stateIDMap.get("3-3-1"))); // LEFT
        stateIDMap.get("4-4-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-4-1"), // UP
                stateIDMap.get("4-4-1"), // RIGHT
                stateIDMap.get("4-3-1"), // DOWN
                stateIDMap.get("3-4-1"))); // LEFT

        /*
        Transitions for level 2
         */
        stateIDMap.get("1-1-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-2-2"), // UP
                stateIDMap.get("2-1-2"), // RIGHT
                stateIDMap.get("1-1-2"), // DOWN
                stateIDMap.get("1-1-2"))); // LEFT
        stateIDMap.get("1-2-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-3-2"), // UP
                stateIDMap.get("2-2-2"), // RIGHT
                stateIDMap.get("1-1-2"), // DOWN
                stateIDMap.get("1-2-2"))); // LEFT
        stateIDMap.get("1-3-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-4-2"), // UP
                stateIDMap.get("2-2-2"), // RIGHT
                stateIDMap.get("1-1-2"), // DOWN
                stateIDMap.get("1-2-2"))); // LEFT
        stateIDMap.get("1-4-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-4-2"), // UP
                stateIDMap.get("2-4-2"), // RIGHT
                stateIDMap.get("1-3-2"), // DOWN
                stateIDMap.get("1-4-2"))); // LEFT
        stateIDMap.get("2-1-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-2-2"), // UP
                stateIDMap.get("3-1-2"), // RIGHT
                stateIDMap.get("2-1-2"), // DOWN
                stateIDMap.get("1-1-2"))); // LEFT
        stateIDMap.get("2-2-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-3-2"), // UP
                stateIDMap.get("3-2-2"), // RIGHT
                stateIDMap.get("2-1-2"), // DOWN
                stateIDMap.get("1-2-2"))); // LEFT
        stateIDMap.get("2-3-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-4-2"), // UP
                stateIDMap.get("3-3-2"), // RIGHT
                stateIDMap.get("2-2-2"), // DOWN
                stateIDMap.get("1-3-2"))); // LEFT
        stateIDMap.get("2-4-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-4-2"), // UP
                stateIDMap.get("3-4-2"), // RIGHT
                stateIDMap.get("2-3-2"), // DOWN
                stateIDMap.get("1-4-2"))); // LEFT
        stateIDMap.get("3-1-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-2-2"), // UP
                stateIDMap.get("4-1-2"), // RIGHT
                stateIDMap.get("3-1-2"), // DOWN
                stateIDMap.get("2-1-2"))); // LEFT
        stateIDMap.get("3-2-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-3-2"), // UP
                stateIDMap.get("4-2-2"), // RIGHT
                stateIDMap.get("3-1-2"), // DOWN
                stateIDMap.get("2-2-2"))); // LEFT
        stateIDMap.get("3-3-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-4-2"), // UP
                stateIDMap.get("4-3-2"), // RIGHT
                stateIDMap.get("3-2-2"), // DOWN
                stateIDMap.get("2-3-2"))); // LEFT
        stateIDMap.get("3-4-2").setTransitions(new PickupTransitionMap(
                stateIDMap.get("3-4-2"), // UP
                stateIDMap.get("4-4-2"), // RIGHT
                stateIDMap.get("3-3-2"), // DOWN
                stateIDMap.get("2-4-2"), // LEFT
                stateIDMap.get("3-4-4"))); // PICKUP
        stateIDMap.get("4-1-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-2-2"), // UP
                stateIDMap.get("4-1-2"), // RIGHT
                stateIDMap.get("4-1-2"), // DOWN
                stateIDMap.get("3-1-2"))); // LEFT
        stateIDMap.get("4-2-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-3-2"), // UP
                stateIDMap.get("4-2-2"), // RIGHT
                stateIDMap.get("4-1-2"), // DOWN
                stateIDMap.get("3-2-2"))); // LEFT
        stateIDMap.get("4-3-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-4-2"), // UP
                stateIDMap.get("4-3-2"), // RIGHT
                stateIDMap.get("4-2-2"), // DOWN
                stateIDMap.get("3-3-2"))); // LEFT
        stateIDMap.get("4-4-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-4-2"), // UP
                stateIDMap.get("4-4-2"), // RIGHT
                stateIDMap.get("4-3-2"), // DOWN
                stateIDMap.get("3-4-2"))); // LEFT

        /*
         Transitions for level 3
         */
        stateIDMap.get("1-2-1").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("wumpus"), // UP
                stateIDMap.get("2-2-3"), // RIGHT
                stateIDMap.get("terminal"), // DOWN
                stateIDMap.get("1-2-3"))); // LEFT
        stateIDMap.get("1-4-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-4-3"), // UP
                stateIDMap.get("2-4-4"), // RIGHT
                stateIDMap.get("wumpus"), // DOWN
                stateIDMap.get("1-4-3"))); // LEFT
        stateIDMap.get("2-1-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-2-3"), // UP
                stateIDMap.get("3-1-3"), // RIGHT
                stateIDMap.get("2-1-3"), // DOWN
                stateIDMap.get("1-1-3"))); // LEFT
        stateIDMap.get("2-2-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-3-3"), // UP
                stateIDMap.get("3-2-3"), // RIGHT
                stateIDMap.get("2-1-3"), // DOWN
                stateIDMap.get("1-2-3"))); // LEFT
        stateIDMap.get("2-3-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-4-4"), // UP
                stateIDMap.get("3-3-3"), // RIGHT
                stateIDMap.get("2-2-3"), // DOWN
                stateIDMap.get("wumpus"))); // LEFT
        stateIDMap.get("3-1-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-2-3"), // UP
                stateIDMap.get("4-1-3"), // RIGHT
                stateIDMap.get("3-1-3"), // DOWN
                stateIDMap.get("2-1-3"))); // LEFT
        stateIDMap.get("3-2-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-3-3"), // UP
                stateIDMap.get("4-2-3"), // RIGHT
                stateIDMap.get("3-1-3"), // DOWN
                stateIDMap.get("2-2-3"))); // LEFT
        stateIDMap.get("3-3-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-4-3"), // UP
                stateIDMap.get("4-3-3"), // RIGHT
                stateIDMap.get("3-2-3"), // DOWN
                stateIDMap.get("2-3-3"))); // LEFT
        stateIDMap.get("3-4-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-4-3"), // UP
                stateIDMap.get("4-4-3"), // RIGHT
                stateIDMap.get("3-3-3"), // DOWN
                stateIDMap.get("2-4-4"))); // LEFT
        stateIDMap.get("4-1-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-2-3"), // UP
                stateIDMap.get("4-1-3"), // RIGHT
                stateIDMap.get("4-1-3"), // DOWN
                stateIDMap.get("3-1-3"))); // LEFT
        stateIDMap.get("4-2-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-3-3"), // UP
                stateIDMap.get("4-2-3"), // RIGHT
                stateIDMap.get("4-1-3"), // DOWN
                stateIDMap.get("3-2-3"))); // LEFT
        stateIDMap.get("4-3-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-4-3"), // UP
                stateIDMap.get("4-3-3"), // RIGHT
                stateIDMap.get("4-2-3"), // DOWN
                stateIDMap.get("3-3-3"))); // LEFT
        stateIDMap.get("4-4-3").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-4-3"), // UP
                stateIDMap.get("4-4-3"), // RIGHT
                stateIDMap.get("4-3-3"), // DOWN
                stateIDMap.get("3-4-3"))); // LEFT

       /*
        Transitions for level 4
         */
        stateIDMap.get("1-2-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-3-4"), // UP
                stateIDMap.get("2-2-4"), // RIGHT
                stateIDMap.get("1-1-4"), // DOWN
                stateIDMap.get("1-2-4"))); // LEFT
        stateIDMap.get("1-3-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-4-4"), // UP
                stateIDMap.get("2-2-4"), // RIGHT
                stateIDMap.get("1-1-4"), // DOWN
                stateIDMap.get("1-2-4"))); // LEFT
        stateIDMap.get("1-4-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("1-4-4"), // UP
                stateIDMap.get("2-4-4"), // RIGHT
                stateIDMap.get("1-3-4"), // DOWN
                stateIDMap.get("1-4-4"))); // LEFT
        stateIDMap.get("2-1-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-2-4"), // UP
                stateIDMap.get("3-1-4"), // RIGHT
                stateIDMap.get("2-1-4"), // DOWN
                stateIDMap.get("1-1-4"))); // LEFT
        stateIDMap.get("2-2-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-3-4"), // UP
                stateIDMap.get("3-2-4"), // RIGHT
                stateIDMap.get("2-1-4"), // DOWN
                stateIDMap.get("1-2-4"))); // LEFT
        stateIDMap.get("2-3-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-4-4"), // UP
                stateIDMap.get("3-3-4"), // RIGHT
                stateIDMap.get("2-2-4"), // DOWN
                stateIDMap.get("1-3-4"))); // LEFT
        stateIDMap.get("2-4-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("2-4-4"), // UP
                stateIDMap.get("3-3-4"), // RIGHT
                stateIDMap.get("2-2-4"), // DOWN
                stateIDMap.get("1-3-4"))); // LEFT
        stateIDMap.get("3-1-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-2-4"), // UP
                stateIDMap.get("4-1-4"), // RIGHT
                stateIDMap.get("3-1-4"), // DOWN
                stateIDMap.get("2-1-4"))); // LEFT
        stateIDMap.get("3-2-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-3-4"), // UP
                stateIDMap.get("4-2-4"), // RIGHT
                stateIDMap.get("3-1-4"), // DOWN
                stateIDMap.get("2-2-4"))); // LEFT
        stateIDMap.get("3-3-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-4-4"), // UP
                stateIDMap.get("4-3-4"), // RIGHT
                stateIDMap.get("3-2-4"), // DOWN
                stateIDMap.get("2-3-4"))); // LEFT
        stateIDMap.get("3-4-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("3-4-4"), // UP
                stateIDMap.get("4-4-4"), // RIGHT
                stateIDMap.get("3-3-4"), // DOWN
                stateIDMap.get("2-4-4"))); // LEFT
        stateIDMap.get("4-1-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-2-4"), // UP
                stateIDMap.get("4-1-4"), // RIGHT
                stateIDMap.get("4-1-4"), // DOWN
                stateIDMap.get("3-1-4"))); // LEFT
        stateIDMap.get("4-2-2").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-3-2"), // UP
                stateIDMap.get("4-2-2"), // RIGHT
                stateIDMap.get("4-1-2"), // DOWN
                stateIDMap.get("3-2-2"))); // LEFT
        stateIDMap.get("4-3-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-4-4"), // UP
                stateIDMap.get("4-3-4"), // RIGHT
                stateIDMap.get("4-2-4"), // DOWN
                stateIDMap.get("3-3-4"))); // LEFT
        stateIDMap.get("4-4-4").setTransitions(new SimpleTransitionMap(
                stateIDMap.get("4-4-4"), // UP
                stateIDMap.get("4-4-4"), // RIGHT
                stateIDMap.get("4-3-4"), // DOWN
                stateIDMap.get("3-4-4"))); // LEFT

        List<State> states = new ArrayList<>(stateIDMap.size());

        states.addAll(stateIDMap.values().stream().collect(Collectors.toList()));

        return states;

    }

    @Override
    public State getStartingState()
    {
        return stateIDMap.get("1-1-1");
    }
}
