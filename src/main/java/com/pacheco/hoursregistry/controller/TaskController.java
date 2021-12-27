package com.pacheco.hoursregistry.controller;

import java.util.List;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.repository.TaskRepository;
import com.pacheco.hoursregistry.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    private List<Task> listTasks(@RequestParam(value="done", required=false) Boolean taskDone) {
        return taskRepository.findByQuery(taskDone);
    }

    @GetMapping("/tasks/{taskId}")
    private Task consultTask(@PathVariable Long taskId) {
        return taskService.consultTask(taskId);
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    private Task registerTask(@RequestBody String taskResume) {
        return taskService.registerTask(taskResume);
    }

    @PutMapping("/tasks/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    @DeleteMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void removeTask(@PathVariable Long taskId) {
        taskService.removeTask(taskId);
    }

}
