package com.samvbeckmann.ai.project1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumerates the actions that can be taken in any state.
 * Note: Any action can be taken in any state, though
 * the result might not change the state an agent is in.
 *
 * @author Sam Beckmann
 */
public enum Action
{
    UP, RIGHT, DOWN, LEFT;

    private static final List<Action> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Action randomAction()
    {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
