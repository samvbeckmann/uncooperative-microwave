package com.samvbeckmann.ai.project2;

/**
 * Defines a Bayesian Network
 * Contains an array of nodes
 *
 * @author Sam Beckmann
 */
class BayesianNetwork
{
    private NetworkNode[] nodes;

    BayesianNetwork(NetworkNode[] nodes)
    {
        this.nodes = nodes;
    }

    /**
     * Gets the IDs of parents of a node.
     *
     * @param node ID of node to get parents from
     * @return int array of parent node IDs
     */
    int[] getParents(int node)
    {
        return nodes[node].getParents();
    }

    /**
     * Get the probability of a given node being in a given state
     * from that node's parent activations.
     *
     * @param node ID of node
     * @param state target state for node
     * @param parents array of node's parents' states
     * @return Probability of node being in state, given parents
     */
    double getProbGivenParents(int node, boolean state, boolean[] parents)
    {
        double prob = nodes[node].getProbFromParents(parents);
        return state ? prob : 1 - prob;
    }

    /**
     * @return The number of nodes in the network
     */
    int getNumNodes()
    {
        return nodes.length;
    }
}