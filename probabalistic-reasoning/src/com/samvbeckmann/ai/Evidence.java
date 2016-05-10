package com.samvbeckmann.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a container for evidence in a {@link DynamicNetwork}
 * or any other interface that takes advantages of the contracts within.
 * Currently only supports boolean evidence variables, this could be
 * changed in the future to support any discrete evidence outcomes.\
 *
 * @author Sam Beckmann
 */
class Evidence
{
    private Map<Integer, Map<String, Boolean>> evidenceHistory;

    Evidence()
    {
        evidenceHistory = new HashMap<>();
    }

    /**
     * Gets a mapping of evidence variables to their states at the given timestep.
     *
     * @param timestep Timestep to retrieve evidence from.
     * @return Mapping of evidence variables to boolean states at the given timestep.
     */
    Map<String, Boolean> getEvidenceAtTimestep(int timestep)
    {
        return evidenceHistory.get(timestep);
    }

    /**
     * Adds the given piece of evidence into this evidence container.
     * Does not overwrite any previous evidence.
     *
     * @param key Evidence variable.
     * @param value State of the evidence variable.
     * @param timestep Time at which this evidence occurred.
     */
    void addEvidence(String key, boolean value, int timestep)
    {
        Map<String, Boolean> currentEvidenceAtTimestep = evidenceHistory.get(timestep);
        if (currentEvidenceAtTimestep == null)
            currentEvidenceAtTimestep = new HashMap<>();
        currentEvidenceAtTimestep.put(key, value);
        evidenceHistory.put(timestep, currentEvidenceAtTimestep);
    }

    /**
     * Adds each piece of evidence in the given mapping into this evidence container.
     * Does not overwrite any previous evidence.
     *
     * @param pairs Evidence variable, State pairs to be added to the evidence container.
     * @param timestep Time at which this evidence occurred.
     */
    void addEvidence(Map<String, Boolean> pairs, int timestep)
    {
        for (String key : pairs.keySet())
            addEvidence(key, pairs.get(key), timestep);
    }
}
