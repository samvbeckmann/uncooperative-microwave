package com.samvbeckmann.ai.project1;

import java.util.List;

/**
 * Defines a maze, which a strategy will create a policy for.
 * See {@link ExampleMaze} for a sample implementation.
 */
public interface Maze
{
    /**
     * @return a list of the states in the maze.
     */
    List<State> getStates();

    /**
     * @return the starting state for a simulation of the maze.
     */
    State getStartingState();

    /**
     * Generate an ascii representation of the policy for quick
     * visualization.
     *
     * @param policy Policy to be visualized.
     * @return Formatted string for visualization.
     */
    String visualizePolicy(Policy policy);
}
