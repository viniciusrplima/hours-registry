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

import static com.pacheco.hoursregistry.util.AuthorizationUtil.currentUsername;


@Service
public class EffortServiceImpl implements EffortService {

    public static final String CANT_FIND_EFFORT = "Can't find effort with id %d";

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
        Effort effort = task.updateUndoneEffortOrCreate();

        return repository.save(effort);
    }

    @Override
    public List<EffortTaskDTO> findDoneEfforts() {
        return repository.findDoneEfforts(currentUsername());
    }

    @Override
    public List<EffortTaskDTO> findUndoneEfforts() {
        return repository.findDoneEfforts(currentUsername());
    }

    @Override
    public List<EffortTaskDTO> findAllEfforts() {
        return repository.findAllEfforts(currentUsername());
    }

    @Override
    public EffortTaskDTO consultEffortTask(Long effortId) throws NoEntityFoundException {
        EffortTaskDTO effort = repository.findEffortTaskById(currentUsername(), effortId)
                .orElseThrow(() -> new NoEntityFoundException(String.format(CANT_FIND_EFFORT, effortId)));

        return effort;
    }
    
}
