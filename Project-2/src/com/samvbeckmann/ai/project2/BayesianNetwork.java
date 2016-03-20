package com.samvbeckmann.ai.project2;

/**
 * Created by sam on 3/16/16.
 */
public class BayesianNetwork
{
    private NetworkNode[] nodes;

    public BayesianNetwork(NetworkNode[] nodes)
    {
        this.nodes = nodes;
    }

    public int[] getParents(int node)
    {
        return nodes[node].getParents();
    }

    public double getProbGivenParents(int node, boolean state, boolean[] parents)
    {
        double prob = nodes[node].getProbFromParents(parents);
        if (!state) prob = 1 - prob;
        return prob;
    }

    public int getNumNodes()
    {
        return nodes.length;
    }
}