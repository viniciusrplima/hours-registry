package com.pacheco.hoursregistry.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.pacheco.hoursregistry.dto.EffortTaskDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Effort;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.repository.EffortRepository;

import com.pacheco.hoursregistry.service.EffortService;
import com.pacheco.hoursregistry.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EffortServiceImpl implements EffortService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EffortRepository repository;

    @Override
    public List<Effort> listEffortsFromTask(Long taskId) throws NoEntityFoundException {
        Task task = taskService.consultTask(taskId);
        return task.getEfforts();
    }

    @Override
    public Effort updateEffort(Long taskId) throws NoEntityFoundException {
        Task task = taskService.consultTask(taskId);
        Effort effort = null;

        for (Effort e : task.getEfforts()) {
            if (e.getTermination() == null) {
                effort = e;
            }
        }

        if (effort == null) {
            effort = new Effort(LocalDateTime.now(), task);
        }
        else {
            effort.setTermination(LocalDateTime.now());
        }

        return repository.save(effort);
    }

    @Override
    public Effort consultEffort(Long effortId) throws NoEntityFoundException {
        Optional<Effort> opEffort = repository.findById(effortId);

        if (opEffort.isEmpty()) {
            throw new NoEntityFoundException(String.format(
                "Can't find effort with id %d", effortId));
        }

        return opEffort.get();
    }

    @Override
    public EffortTaskDTO consultEffortTask(Long effortId) throws NoEntityFoundException {
        Optional<EffortTaskDTO> opEffort = repository.findEffortTaskById(effortId);

        if (opEffort.isEmpty()) {
            throw new NoEntityFoundException(String.format(
                "Can't find effort with id %d", effortId));
        }

        return opEffort.get();
    }
    
}
