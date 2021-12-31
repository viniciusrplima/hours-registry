package com.pacheco.hoursregistry.controller;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.repository.TaskRepository;
import com.pacheco.hoursregistry.service.TaskService;
import com.pacheco.hoursregistry.util.AuthorizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private AuthorizationUtil auth;

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/tasks")
    public ModelAndView getTasks() {
        List<Task> tasks = taskService.listTasksFromUser();

        ModelAndView mv = new ModelAndView("tasks");
        mv.addObject("tasks", tasks);

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
}
