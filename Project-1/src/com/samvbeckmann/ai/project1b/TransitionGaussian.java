package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;

import java.util.Random;

/**
 * Created by sam on 2/15/16.
 */
public class TransitionGaussian implements TransitionModel
{
    private double meanMagnitude;
    private double sdMagnitude;
    private double sdAngle;
    private Random rnd = new Random();

    public TransitionGaussian(double meanMagnitude, double sdMagnitude, double sdAngle)
    {
        this.meanMagnitude = meanMagnitude;
        this.sdMagnitude = sdMagnitude;
        this.sdAngle = sdAngle;
    }

    @Override
    public Coordinate getNewCoordinate(Coordinate current, Action action)
    {
        double angle = rnd.nextGaussian() * sdAngle;
        angle += offsetAngleFromAction(action);
        double magnitude = meanMagnitude + rnd.nextGaussian() * sdMagnitude;

        double y = Math.sin(Math.toRadians(angle)) * magnitude;
        double x = Math.cos(Math.toRadians(angle)) * magnitude;

        return current.offsetCoordinate(x, y);
    }

    @Override
    public Coordinate getAverageTransition(Coordinate current, Action action)
    {
        double angle = offsetAngleFromAction(action);
        double y = Math.sin(Math.toRadians(angle)) * meanMagnitude;
        double x = Math.sin(Math.toRadians(angle)) * meanMagnitude;

        return current.offsetCoordinate(x, y);
    }

    private double offsetAngleFromAction(Action action) // TODO: Move to action
    {
        switch (action)
        {
            case UP:
                return 90;
            case RIGHT:
                return 0;
            case DOWN:
                return 270;
            case LEFT:
                return 180;
            default:
                return 0; // TODO: Make into throwing an error
        }
    }
}
