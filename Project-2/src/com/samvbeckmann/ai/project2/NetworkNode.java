package com.samvbeckmann.ai.project2;

/**
 * Created by sam on 3/16/16.
 */
public class NetworkNode
{
    private int[] parents;
    private double[] probabilities;

    public NetworkNode(int[] parents, double[] probabilities) {
        this.parents = parents;
        this.probabilities = probabilities;
    }

    public Double getProbFromParents(boolean[] parents) {
        int resultLocation = 0;
        for (int i = 0; i < parents.length; i++)
            if (!parents[i])
                resultLocation += 1 << i;
        return probabilities[resultLocation];
    }

    public int[] getParents()
    {
        return parents;
    }
}
