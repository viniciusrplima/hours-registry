package com.pacheco.hoursregistry.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EffortTaskDTO {
    
    private Long id;

    private LocalDateTime initial;

    private LocalDateTime termination;

    private String taskResume;

    private Boolean taskDone;

}
