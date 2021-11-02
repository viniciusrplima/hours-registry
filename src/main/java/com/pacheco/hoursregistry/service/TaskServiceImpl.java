package com.pacheco.hoursregistry.service;

import java.util.Optional;

import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repository;

    @Override
    public Task registerTask(String taskResume) {
        Task task = new Task(taskResume);

        return repository.save(task);
    }

    @Override
    public Task consultTask(Long taskId) throws NoEntityFoundException {
        Optional<Task> opTask = repository.findById(taskId);

        if (opTask.isEmpty()) {
            throw new NoEntityFoundException(String.format("Can't found task with id %d", taskId));
        }

        return opTask.get();
    }

    @Override
    public void removeTask(Long taskId) {
        repository.deleteById(taskId);
    }

    @Override
    public Task saveTask(Task task) {
        return repository.save(task);
    }

}
