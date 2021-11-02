package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Task;

public interface TaskService {

    public Task registerTask(String taskResume);

    public Task consultTask(Long taskId) throws NoEntityFoundException;

    public void removeTask(Long taskId);

    public Task updateTask(Long taskId, TaskDTO taskDTO) throws NoEntityFoundException;

    public Task saveTask(Task task);

}
