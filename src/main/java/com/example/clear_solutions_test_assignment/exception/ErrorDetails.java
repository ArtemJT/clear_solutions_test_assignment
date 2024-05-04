package com.example.clear_solutions_test_assignment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
public record ErrorDetails(
        String timestamp,
        String path,
        Map<String, String> errors) {

    public static ResponseEntity<ErrorDetails> getResponseEntity(String path,
                                                                 HttpStatus status,
                                                                 Map<String, String> errors) {
        LocalDateTime now = LocalDateTime.now();
        String formattedTimestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ErrorDetails errorDetails = new ErrorDetails(
                formattedTimestamp,
                path,
                errors);
        log.error(errorDetails.toString());
        return new ResponseEntity<>(errorDetails, status);
    }
}
