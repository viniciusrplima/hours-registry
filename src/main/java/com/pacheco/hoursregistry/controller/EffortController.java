package com.pacheco.hoursregistry.controller;

import java.util.List;
import java.util.Optional;

import com.pacheco.hoursregistry.dto.EffortTaskDTO;
import com.pacheco.hoursregistry.model.Effort;
import com.pacheco.hoursregistry.service.EffortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping
public class EffortController {

    @Autowired
    private EffortService effortService;

    @GetMapping("/tasks/{taskId}/efforts")
    public List<Effort> listEffortsFromTask(@PathVariable Long taskId) {
        return effortService.listEffortsFromTask(taskId);
    }

    @GetMapping("/efforts/{effortId}")
    private EffortTaskDTO consultEffort(@PathVariable Long effortId) {
        return effortService.consultEffortTask(effortId);
    }

    @GetMapping("/efforts")
    private List<EffortTaskDTO> listEffortsUndone(@RequestParam(value="done", required=false) Optional<Boolean> effortDone) {
        if (effortDone.isPresent()) {
            if (effortDone.get()) {
                return effortService.findDoneEfforts();
            }
            else {
                return effortService.findUndoneEfforts();
            }
        }
        else {
            return effortService.findAllEfforts();
        }
    }

    @PostMapping("/tasks/{taskId}/efforts")
    private Effort updateEffort(@PathVariable Long taskId) {
        return effortService.updateEffort(taskId);
    }

}
