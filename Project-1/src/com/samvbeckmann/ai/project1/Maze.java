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
}
