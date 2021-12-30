package com.pacheco.hoursregistry.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {

    private Integer status;
    private String title;
    private String detail;
    private LocalDateTime timestamp;
    private String userMessage;

}
