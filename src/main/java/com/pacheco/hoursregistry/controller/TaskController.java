package com.pacheco.hoursregistry.controller;

import java.util.List;
import java.util.Optional;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.repository.TaskRepository;
import com.pacheco.hoursregistry.service.TaskService;
import com.pacheco.hoursregistry.util.CustomErrorMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping
    private List<Task> listTasks(@RequestParam(value="done", required=false) Optional<Boolean> taskDone) {
        if (taskDone.isPresent()) {
            return taskRepository.findTasksByDone(taskDone.get());
        }
        else {
            return taskRepository.findAll();
        }
    }

    @GetMapping("/{taskId}")
    private ResponseEntity<?> consultTask(@PathVariable Long taskId) {
        try {
            Task task = taskService.consultTask(taskId);
            return ResponseEntity.ok(task);
        }
        catch (NoEntityFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new CustomErrorMessage(e.getMessage()));
        }
    }

    @PostMapping
    private ResponseEntity<?> registerTask(@RequestBody String taskResume) {
        Task task = taskService.registerTask(taskResume);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{taskId}")
    private ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        try {
            Task task = taskService.updateTask(taskId, taskDTO);
            return ResponseEntity.ok(task);
        }
        catch (NoEntityFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new CustomErrorMessage(e.getMessage()));
        }
    }

    @DeleteMapping("/{taskId}")
    private void removeTask(@PathVariable Long taskId) {
        taskService.removeTask(taskId);
    }

}
