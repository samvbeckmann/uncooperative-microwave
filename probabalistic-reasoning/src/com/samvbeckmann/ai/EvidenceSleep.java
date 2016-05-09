package com.samvbeckmann.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 5/4/16.
 */
public class EvidenceSleep implements IEvidence
{
    private Map<Integer, Map<String, Boolean>> evidenceHistory;

    public EvidenceSleep()
    {
        evidenceHistory = new HashMap<>();
    }

    @Override
    public Map<String, Boolean> getEvidenceAtTimestep(int timestep)
    {
        return evidenceHistory.get(timestep);
    }

    @Override
    public void addEvidence(String key, boolean value, int timestep)
    {
        Map<String, Boolean> currentEvidenceAtTimestep = evidenceHistory.get(timestep);
        currentEvidenceAtTimestep.put(key, value);
        evidenceHistory.put(timestep, currentEvidenceAtTimestep);
    }

    @Override
    public void addEvidence(Map<String, Boolean> pairs, int timestep)
    {
        for (String key : pairs.keySet())
            addEvidence(key, pairs.get(key), timestep);
    }
}
