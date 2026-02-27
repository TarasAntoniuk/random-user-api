package com.tarasantoniuk.random_user_api.common.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
