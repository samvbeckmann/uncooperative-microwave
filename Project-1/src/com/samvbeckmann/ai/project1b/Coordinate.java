package com.samvbeckmann.ai.project1b;

/**
 * Defines an x,y coordinate in the continuous-space world.
 * Used to keep track of a position.
 *
 * @author Sam Beckmann
 */
public class Coordinate
{
    private double x;
    private double y;

    /**
     * Construct a coordinate with x and y positions.
     *
     * @param x position on x axis
     * @param y position on y axis
     */
    public Coordinate(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new coordinate, offset from this coordinate
     * by the given x and y values.
     *
     * @param x offset in x axis
     * @param y offset in y axis
     * @return new Coordinate, offset by x and y
     */
    public Coordinate offsetCoordinate(double x, double y)
    {
        double offsetX = getX() + x;
        double offsetY = getY() + y;

        return new Coordinate(offsetX, offsetY);
    }

    /**
     * Moves this coordinate by the given x and y values.
     * Differs from offsetCoordinate in that it does not
     * make a new coordinate, but modifies the current one.
     *
     * @param x Movement in x axis
     * @param y Movement in y axis
     */
    public void moveCoordinate(double x, double y)
    {
        setX(getX() + x);
        setY(getY() + y);
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

    /**
     * Gets the absolute difference between this coordinate and another coordinate.
     *
     * @param otherCoord Other coordinate to get distance to
     * @return absolute distance between the two coordinates
     */
    public double getDistance(Coordinate otherCoord)
    {
        double xDifference = x - otherCoord.getX();
        double yDifference = y - otherCoord.getY();

        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }
}
