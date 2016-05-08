package com.samvbeckmann.ai;

import java.util.Map;

/**
 * Keeps track of evidence for a dynamic bayesian network.
 * This implementation only support boolean values.
 */
public interface IEvidence
{
    public Map<String, Boolean> getEvidenceAtTimestep(int timestep);

    public void addEvidence(String key, boolean value, int timestep);

    public void addEvidence(Map<String, Boolean> pairs, int timestep);
}
