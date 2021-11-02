package com.pacheco.hoursregistry.service;

import java.util.List;

import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Effort;

public interface EffortService {
    
    public List<Effort> listEffortsFromTask(Long taskId) throws NoEntityFoundException;

    public Effort updateEffort(Long taskId) throws NoEntityFoundException;

}
