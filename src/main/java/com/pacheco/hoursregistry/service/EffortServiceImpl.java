package com.pacheco.hoursregistry.service;

import java.time.LocalDateTime;
import java.util.List;

import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Effort;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.repository.EffortRepository;

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
    
}
