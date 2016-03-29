package com.samvbeckmann.ai.project2;

/**
 * Defines a Bayesian Network
 * Contains an array of nodes
 *
 * @author Sam Beckmann
 */
class BayesianNetwork
{
    private final NetworkNode[] nodes;

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

    double[] getDistribution(int node, BayesianEvent event)
    {
        return nodes[node].getDistribution(event);
    }

    /**
     * Get the probability of a given node being in a given state
     * from that node's parent activations.
     *
     * @param node    ID of node
     * @param state   target state for node
     * @param event   TODO
     * @return Probability of node being in state, given parents
     */
    double getProbGivenParents(int node, int state, BayesianEvent event)
    {
        return nodes[node].getProbFromParents(event, state);
    }

    /**
     * Gets the probability of a node given it's Markov Blanket.
     *
     * @param node
     * @param state
     * @param event
     * @return
     */
    double getProbGivenMB(int node, int state, BayesianEvent event)
    {
        double probability = nodes[node].getProbFromParents(event, state);
        for (int child : nodes[node].children)
            probability *= nodes[child].getProbFromParents(event, event.getEvidenceAtID(child));
        return probability;
    }

    double[] getMBDistribution(int node, BayesianEvent event)
    {
        double[] result = new double[getNumStates(node)];
        for (int i = 0; i < getNumStates(node); i++)
            result[i] = getProbGivenMB(node, i, event);
        return BayesianHelper.normalize(result);
    }

    /**
     * @return The number of nodes in the network
     */
    int getNumNodes()
    {
        return nodes.length;
    }

    int getNumStates(int nodeID)
    {
        return nodes[nodeID].getNumStates();
    }

    /**
     * Defines a node in a network.
     * A node contains an array of parent IDs, and
     * a probability table.
     */
    private class NetworkNode
    {
        private final int[] parentIDs;
        private final int[] children;
        private final double[][] probabilities;
        private final int numStates;

        private NetworkNode(int[] parentIDs, double[][] probabilities, int domain, int[] children)
        {
            this.parentIDs = parentIDs;
            this.probabilities = probabilities;
            this.numStates = domain;
            this.children = children;
        }

        /**
         * Gets the specific probability from the probability table
         * from an array of parents activations.
         *
         * @param event Bayesian Event that contains the parents states
         * @return Specific probability from parents' states
         */
        double getProbFromParents(BayesianEvent event, int state)
        {
            return getDistribution(event)[state];
        }

        double[] getDistribution(BayesianEvent event)
        {
            int lookup = 0;
            int startSize = probabilities.length;
            for(int i  = parentIDs.length - 1; i >= 0; i--)
            {
                startSize /= nodes[i].getNumStates();
                lookup += startSize * event.getEvidenceAtID(i);
            }
            return probabilities[lookup];
        }

        int[] getParents()
        {
            return parentIDs;
        }

        int getNumStates()
        {
            return numStates;
        }
    }
}