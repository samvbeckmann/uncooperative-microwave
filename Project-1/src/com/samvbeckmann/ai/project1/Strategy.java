package com.samvbeckmann.ai.project1;

import java.util.List;

/**
 * Created by sam on 1/22/16.
 */
public abstract class Strategy
{
    public abstract Policy generatePolicy(List<State> states, float discount, float epsilon);
}
