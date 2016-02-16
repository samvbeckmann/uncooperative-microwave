package com.samvbeckmann.ai.project1b;

import java.util.List;

/**
 * Created by sam on 2/15/16.
 */
public interface FeatureSet
{
    List<Feature> getFeaturesAtCoordinate(Coordinate coordinate);
}
