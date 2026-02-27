package com.tarasantoniuk.random_user_api.common.exception;

import com.tarasantoniuk.random_user_api.common.dto.ErrorResponse;
import com.tarasantoniuk.random_user_api.feature.user.exception.ExternalApiException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorResponse> handleExternalApiException(ExternalApiException ex){
        ErrorResponse error = new ErrorResponse(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex){
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(500, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
