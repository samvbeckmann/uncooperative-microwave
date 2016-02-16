package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;

/**
 * Created by sam on 2/15/16.
 */
public interface TransitionModel
{
    Coordinate getNewCoordinate(Coordinate current, Action action);

    Coordinate getAverageTransition(Coordinate current, Action action);
}
