package com.pacheco.hoursregistry.service.impl;

import java.util.List;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.TaskRepository;

import com.pacheco.hoursregistry.service.TaskService;
import com.pacheco.hoursregistry.service.UserService;
import com.pacheco.hoursregistry.util.AuthorizationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

    public static final String CANT_FIND_TASK = "Can't found task with id %d";

    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    @Override
    public Task registerTask(String taskResume, String username) throws NoEntityFoundException {
        User user = userService.find(username);
        Task task = new Task(taskResume, user);

        return repository.save(task);
    }

    @Override
    public Task consultTask(Long taskId, String username) throws NoEntityFoundException {
        return repository.findTaskByUserUsernameAndId(username, taskId)
                .orElseThrow(() -> new NoEntityFoundException(String.format(CANT_FIND_TASK, taskId)));
    }

    @Override
    @Transactional
    public void removeTask(Long taskId, String username) {
        consultTask(taskId, username);
        repository.deleteByUserUsernameAndId(username, taskId);
    }

    private Task saveTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskDTO taskDTO, String username) throws NoEntityFoundException {
        Task taskActual = consultTask(taskId, username);
        BeanUtils.copyProperties(taskDTO, taskActual, "id");

        return saveTask(taskActual);
    }
}