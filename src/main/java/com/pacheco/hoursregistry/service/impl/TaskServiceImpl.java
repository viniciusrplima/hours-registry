package com.pacheco.hoursregistry.service.impl;

import java.util.List;
import java.util.Optional;

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

    private String currentUsername() {
        return authorizationUtil.currentUsername();
    }

    @Override
    public List<Task> findAllTasks() {
        return repository.findTasksByUserUsername(currentUsername());
    }

    @Override
    public List<Task> findTasksByDone(Boolean done) {
        return repository.findTasksByUserUsernameAndDone(currentUsername(), done);
    }

    @Override
    public Task registerTask(String taskResume) throws NoEntityFoundException {
        User user = userService.find(currentUsername());
        Task task = new Task(taskResume, user);

        return repository.save(task);
    }

    @Override
    public Task consultTask(Long taskId) throws NoEntityFoundException {
        return repository.findTaskByUserUsernameAndId(currentUsername(), taskId)
                .orElseThrow(() -> new NoEntityFoundException(String.format(CANT_FIND_TASK, taskId)));
    }

    @Override
    @Transactional
    public void removeTask(Long taskId) {
        consultTask(taskId);
        repository.deleteByUserUsernameAndId(currentUsername(), taskId);
    }

    private Task saveTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskDTO taskDTO) throws NoEntityFoundException {
        Task taskActual = consultTask(taskId);
        BeanUtils.copyProperties(taskDTO, taskActual, "id");

        return saveTask(taskActual);
    }
}