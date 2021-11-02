package com.pacheco.hoursregistry.controller;

import java.util.List;

import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Effort;
import com.pacheco.hoursregistry.repository.EffortRepository;
import com.pacheco.hoursregistry.service.EffortService;
import com.pacheco.hoursregistry.util.CustomErrorMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EffortController {

    @Autowired
    private EffortService effortService;

    @Autowired
    private EffortRepository effortRepository;

    @GetMapping("/task/{taskId}/effort")
    private ResponseEntity<?> listEffortsFromTask(@PathVariable Long taskId) {
        try {
            List<Effort> efforts = effortService.listEffortsFromTask(taskId);
            return ResponseEntity.ok(efforts);
        }
        catch (NoEntityFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new CustomErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("/efforts/undone")
    private List<Effort> listEffortsUndone() {
        return effortRepository.findUndoneEfforts();
    }

    @PostMapping("/task/{taskId}/effort")
    private ResponseEntity<?> updateEffort(@PathVariable Long taskId) {
        try {
            Effort effort = effortService.updateEffort(taskId);
            return ResponseEntity.ok(effort);
        }
        catch (NoEntityFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new CustomErrorMessage(e.getMessage()));
        }
    }

}
