package com.samvbeckmann.ai.project1;

/**
 * Created by sam on 1/22/16.
 */
public class ExpectedUtility
{
    private Action action;
    private float utility;

    public ExpectedUtility(Action action, float utility)
    {
        this.action = action;
        this.utility = utility;
    }

    public Action getAction()
    {
        return action;
    }

    public float getUtility()
    {
        return utility;
    }

    public void setAction(Action action)
    {
        this.action = action;
    }

    public void setUtility(float utility)
    {
        this.utility = utility;
    }
}
