package com.samvbeckmann.ai.project1b;

import com.samvbeckmann.ai.project1.Action;

import java.util.Random;

/**
 * Continuous space TransitionModel, with gaussian offsets.
 *
 * @author Sam Beckmann
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
        angle += Action.getOffsetAngle(action);
        double magnitude = meanMagnitude + rnd.nextGaussian() * sdMagnitude;

        double y = Math.sin(Math.toRadians(angle)) * magnitude;
        double x = Math.cos(Math.toRadians(angle)) * magnitude;

        return current.offsetCoordinate(x, y);
    }
}
