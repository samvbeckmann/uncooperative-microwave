package com.samvbeckmann.ai;

/**
 * Wrapper class around an action and it's expected utility.
 * Without context, not worth particularly much as state is unknown.
 *
 * @author Sam Beckmann
 */
public class ExpectedUtility implements Comparable<ExpectedUtility>
{
    private Action action;
    private double utility;

    public ExpectedUtility(Action action, double utility)
    {
        this.action = action;
        this.utility = utility;
    }

    public Action getAction()
    {
        return action;
    }

    public double getUtility()
    {
        return utility;
    }

    public void setAction(Action action)
    {
        this.action = action;
    }

    public void setUtility(double utility)
    {
        this.utility = utility;
    }

    @Override
    public int compareTo(ExpectedUtility o)
    {
        if (this.getUtility() == o.getUtility())
            return 0;
        else if(this.getUtility() > o.getUtility())
            return 1;
        else
            return -1;
    }
}
