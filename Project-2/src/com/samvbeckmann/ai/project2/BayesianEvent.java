package com.samvbeckmann.ai.project2;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by sam on 3/22/16.
 */
public class BayesianEvent
{
    private Map<Integer, Integer> eventEvidence;
    private Random rnd = new Random();

    public BayesianEvent(Map<Integer, Integer> eventEvidence)
    {
        this.eventEvidence = eventEvidence;
    }

    public BayesianEvent(BayesianEvent event)
    {
        this.eventEvidence = new HashMap<>(event.eventEvidence);
    }

    /**
     * Gets the evidence
     * @param id
     * @return
     */
    @Nullable
    public Integer getEvidenceAtID(int id)
    {
        return eventEvidence.get(id);
    }

    public void addEvidence(int id, int value)
    {
        eventEvidence.put(id, value);
    }

    public void removeEvidence(int id)
    {
        eventEvidence.remove(id);
    }
}
