package com.samvbeckmann.ai;

import java.util.Map;

/**
 * Created by sam on 5/4/16.
 */
public class EvidenceSleep implements IEvidence
{
    public EvidenceSleep()
    {

    }

    @Override
    public Map<String, Boolean> getEvidenceAtTimestep(int timestep)
    {
        return null;
    }

    @Override
    public void addEvidence(String key, boolean value, int timestep)
    {

    }

    @Override
    public void addEvidence(Map<String, Boolean> pairs, int timestep)
    {

    }
}
