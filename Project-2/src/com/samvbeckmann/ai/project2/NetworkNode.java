package com.samvbeckmann.ai.project2;

/**
 * Defines a node in a network.
 * A node contains an array of parent IDs, and
 * a probability table.
 */
class NetworkNode
{
    private int[] parents;
    private double[] probabilities;

    NetworkNode(int[] parents, double[] probabilities)
    {
        this.parents = parents;
        this.probabilities = probabilities;
    }

    /**
     * Gets the specific probability from the probability table
     * from an array of parents activations.
     *
     * @param parents Array of parents states
     * @return Specific probability from parents' states
     */
    Double getProbFromParents(boolean[] parents)
    {
        int resultLocation = 0;
        for (int i = 0; i < parents.length; i++)
            if (!parents[i])
                resultLocation += 1 << i;
        return probabilities[resultLocation];
    }

    int[] getParents()
    {
        return parents;
    }
}
