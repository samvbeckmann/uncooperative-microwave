package com.samvbeckmann.ai.project1b;

import java.util.List;

/**
 * Created by sam on 2/15/16.
 */
public interface World
{
    Coordinate normalizeCoordinate(Coordinate coordinate);

    boolean isTerminal(Coordinate coordinate);

    double getReward(Coordinate coordinate);

    Coordinate getStartingPosition();

    double getXRange();

    double getYRange();

    Coordinate getLowerLeftCoordinate();

    List<Feature> getFeaturesAtCoordinate(Coordinate coordinate);
}
