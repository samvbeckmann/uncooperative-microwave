# Uncooperative Microwave
Projects for CS 7313: Advanced Artificial Intelligence

##Project 1: MDPs
Project 1 dealt with optimal policies for MDPs (Markov Decision Problems).

###Project 1a: Value & Policy Iteration
Project 1a consisted of approximate algorithms for generating optimal policies for MDPs, provided the transition model.

####Value Iteration
The value iteration algorithm constantly updates the utilities of each square until the largest change in utility is less than some user-specified threshold.

####Policy Iteration
The policy iteration algorithm chooses a policy and keeps tweaking it until it stabilizes.

###Project 1b: Q-Learning
Project 1b consisted of using Tile Encoding and Radial Basis Function algorithms to approximate the optimal policy for a continuous-space MDP, with no provided transition model.

In both cases, a function, `theta(x)`, was learned that used weighted inuts to determine the optimal policy.

####Tile Encoding
Tile encoding consists of making a several sets of tiles (known as a tiling) that each capture the entirety of the continuous input space. Then, a weighted function, `theta(x)`, based off of the corresponding tiles (using eligibility traces; think backproopgation) predicts the next action.

####Radial Basis
The difference between radial basis and tile encoding is that, rather than tilings which have a set of binary features, radial basis uses some function (in this case, Gaussian decay) of the distance between the feature and the point in the input space to generate the values for the variables in the weighted function, theta(x).


##Project 2: Inference in Bayesian Networks
Project 2 dealt with inference in Bayesian Networks; that is, given hidden variables, evidence, and a query variable, determine the probability distribution over the query variable.

###Exact Inference: Enumeration Ask
The Enumration Ask algorithm determined the exact distribution for the query value by iterating over the entire network, which made it intractable in large networks.

###Approximate Inference: Likelihood Weighting
Likelihood weighting uses the parents of each node to approximate the value of the query node, then weight that value according to the probability of the node given its parents.

###Approximate Inference: Gibbs Sampling (Monte-Carlo Markov Chain implementation)
Gibbs Sampling assigned random values (proportional to probability) to all of the hidden variables in the network, then would change the value and see how likely the new value is, given the Markov blanket.

> Thanks to [@nateyb](github.com/nateyb) for the project descriptions.
