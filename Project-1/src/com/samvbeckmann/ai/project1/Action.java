package com.samvbeckmann.ai.project1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumerates the cardinal actions that can be taken in any state.
 * Note: Any action can be taken in any state, though
 * the result might not change the state an agent is in.
 *
 * @author Sam Beckmann
 */
@SuppressWarnings("Duplicates")
public enum Action
{
    UP, RIGHT, DOWN, LEFT;

    private static final List<Action> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    /**
     * @return A random action.
     */
    public static Action randomAction()
    {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    /**
     * Gets the enxt action clockwise from the given action.
     *
     * @param action Action to get rotation for
     * @return Next action clockwise from the given action
     */
    public static Action transformClockwise(Action action)
    {
        return Action.values()[(action.ordinal() + 1) % Action.SIZE];
    }

    /**
     * Gets the next action counterclockwise from the given action.
     *
     * @param action Action to get rotation for
     * @return Next action counterclockwise to given action
     */
    public static Action transformCounterclockwise(Action action)
    {
        return Action.values()[((action.ordinal() + (Action.SIZE - 1)) % Action.SIZE)];
    }

    /**
     * Gets an offset angle for the action, using standard trig
     * notation of the positive x-axis being 0 degrees,
     * and moving counterclockwise.
     *
     * @param action Action to get angle from
     * @return Angle, in degrees, associated with the given action.
     */
    public static double getOffsetAngle(Action action)
    {
        switch (action)
        {
            case UP:
                return 90;
            case RIGHT:
                return 0;
            case DOWN:
                return 270;
            case LEFT:
                return 180;
            default:
                return 0; // TODO: Make into throwing an error
        }
    }
}
