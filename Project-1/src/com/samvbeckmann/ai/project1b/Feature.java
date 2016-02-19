package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;

/**
 * Defines a Feature in the state space.
 * A Feature has an id, and an associated action.
 * The action is for calculating Q-values.
 *
 * @author Sam Beckmann
 */
public class Feature
{
    /**
     * ID associated with this feature.
     * Features can share an id, so long
     * as their actions are unique.
     * ID's are immutable.
     */
    private final String id;

    /**
     * Action associated with this feature.
     * Actions are immutable.
     */
    final Action action;

    public Feature(String id, Action action)
    {
        this.id = id;
        this.action = action;
    }

    public String getId()
    {
        return id;
    }

    public Action getAction()
    {
        return action;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj instanceof Feature)
        {
            Feature that = (Feature) obj;
            return (id.equals(that.id) && action.equals(that.action));
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int result = 31;
        result *= id.hashCode();
        result *= action.ordinal();
        return result;
    }
}
