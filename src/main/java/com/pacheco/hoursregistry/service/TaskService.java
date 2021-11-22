package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Task;

import java.util.List;

public interface TaskService {

    public Task registerTask(String taskResume, String username);

    public Task consultTask(Long taskId, String username) throws NoEntityFoundException;

    public void removeTask(Long taskId, String username);

    public Task updateTask(Long taskId, TaskDTO taskDTO, String username) throws NoEntityFoundException;

}
