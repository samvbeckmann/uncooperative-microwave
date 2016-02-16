package com.samvbeckmann.ai.project1b;

/**
 * Created by sam on 2/15/16.
 */
public class Coordinate
{
    private double x;
    private double y;

    public Coordinate(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Coordinate offsetCoordinate(Coordinate addCoord)
    {
        return offsetCoordinate(addCoord.getX(), addCoord.getY());
    }

    public Coordinate offsetCoordinate(double x, double y)
    {
        double offsetX = getX() + x;
        double offsetY = getY() + y;

        return new Coordinate(offsetX, offsetY);
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }
}
