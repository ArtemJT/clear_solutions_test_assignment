package com.example.clear_solutions_test_assignment.exception.handler;

import com.example.clear_solutions_test_assignment.exception.DateRangeException;
import com.example.clear_solutions_test_assignment.exception.ErrorDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.clear_solutions_test_assignment.exception.ErrorDetails.getResponseEntity;

@ControllerAdvice
public record GlobalExceptionHandler() {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(HttpServletRequest request,
                                                                       MethodArgumentNotValidException ex) {
        Map<String, String> errorsMap = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        field -> field.getDefaultMessage() != null ? field.getDefaultMessage() : ""));
        return getResponseEntity(request.getRequestURI(), HttpStatus.BAD_REQUEST, errorsMap);
    }

    @ExceptionHandler({
            SQLException.class,
            EntityNotFoundException.class,
            DateRangeException.class,
            DateTimeParseException.class
    })
    ResponseEntity<ErrorDetails> handleDefaultException(HttpServletRequest request, Exception ex) {
        return getResponseEntity(request.getRequestURI(), HttpStatus.BAD_REQUEST, makeMapFromException(ex));
    }

    private Map<String, String> makeMapFromException(Exception exceptions) {
        String localizedMessage = exceptions.getLocalizedMessage() != null
                ? exceptions.getLocalizedMessage()
                : "No exception message";
        return Map.of(exceptions.getClass().getSimpleName(), localizedMessage);
    }

}
