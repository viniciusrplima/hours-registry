package com.pacheco.hoursregistry.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.repository.TaskRepository;
import com.pacheco.hoursregistry.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping("/api/tasks")
public class TaskApiController {
    
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    private List<Task> listTasks(@RequestParam(value="done", required=false) Boolean taskDone) {
        Link link = linkTo(methodOn(TaskApiController.class).consultTask(2L)).withSelfRel();
        return taskRepository.findByQuery(taskDone).stream()
                .map((task) -> {
                    Link selfLink = linkTo(TaskApiController.class).slash(task.getId()).withSelfRel();
                    task.add(selfLink);
                    return task;
                }).collect(Collectors.toList());
    }

    @GetMapping("/{taskId}")
    private Task consultTask(@PathVariable Long taskId) {
        return taskService.consultTask(taskId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Task registerTask(@RequestBody String taskResume) {
        return taskService.registerTask(taskResume);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void removeTask(@PathVariable Long taskId) {
        taskService.removeTask(taskId);
    }

}
