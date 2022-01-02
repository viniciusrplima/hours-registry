package com.pacheco.hoursregistry.controller;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.model.Model;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
public class PageController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/tasks")
    public ModelAndView getTasks() {
        List<Task> tasks = taskService.listTasksFromUser().stream()
                .map(PageController::addTaskLinks)
                .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("tasks");
        mv.addObject("tasks", tasks);

        return mv;
    }

    @GetMapping("/task/{id}")
    public ModelAndView getTask(@PathVariable Long id) {
        Task task = taskService.consultTask(id);

        ModelAndView mv = new ModelAndView("task");
        mv.addObject("task", task);

        return mv;
    }

    @GetMapping("/new-task")
    public String getTaskForm() {
        return "new-task";
    }

    @PostMapping("/new-task")
    public String saveTask(TaskDTO taskDTO) {
        taskService.registerTask(taskDTO.getResume());
        return "redirect:tasks";
    }

    private static Task addTaskLinks(Task task) {
        Link selfLink = linkTo(methodOn(PageController.class).getTask(task.getId())).withSelfRel();
        task.add(selfLink);
        return task;
    }

}
