package com.pacheco.hoursregistry.exceptionhandler;


import com.pacheco.hoursregistry.exception.BusinessException;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_ERROR_MSG = "A unexpected error ocurred. Try again, if the " +
            "error persists contact the support";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusicessException(BusinessException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NoEntityFoundException.class)
    public ResponseEntity<?> handleNoEntityFoundException(NoEntityFoundException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType errorType = ErrorType.ENTITY_NOT_FOUND;
        String detail = e.getMessage();

        ErrorResponse errorResponse = createErrorResponseBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType errorType = ErrorType.INTERNAL_ERROR;
        String detail = e.getMessage();

        ErrorResponse errorResponse = createErrorResponseBuilder(status, errorType, detail)
                .userMessage(INTERNAL_ERROR_MSG)
                .build();

        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        }
        else if (body instanceof String) {
            body = ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ErrorResponse.ErrorResponseBuilder createErrorResponseBuilder(HttpStatus status, ErrorType type, String detail) {
        return ErrorResponse.builder()
                .status(status.value())
                .title(type.name())
                .timestamp(LocalDateTime.now())
                .detail(detail);
    }

}
