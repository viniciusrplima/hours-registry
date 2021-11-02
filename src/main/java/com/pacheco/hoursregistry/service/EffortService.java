package com.pacheco.hoursregistry.service;

import java.util.List;

import com.pacheco.hoursregistry.dto.EffortTaskDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Effort;

public interface EffortService {
    
    public List<Effort> listEffortsFromTask(Long taskId) throws NoEntityFoundException;

    public Effort consultEffort(Long effortId) throws NoEntityFoundException;

    public EffortTaskDTO consultEffortTask(Long effortId) throws NoEntityFoundException;

    public Effort updateEffort(Long taskId) throws NoEntityFoundException;

}
