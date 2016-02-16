package com.samvbeckmann.ai.project1b;

/**
 * Created by sam on 2/15/16.
 */
public class Feature
{
    private String id;

    public Feature(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj instanceof Feature)
        {
            Feature that = (Feature) obj;
            return (id.equals(that.id));
        }
        return false;
    }
}
