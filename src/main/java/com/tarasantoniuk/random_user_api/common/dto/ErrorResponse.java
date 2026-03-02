package com.tarasantoniuk.random_user_api.common.dto;

import java.time.LocalDateTime;

public record ErrorResponse(int status,
                            String message,
                            LocalDateTime timestamp) {

    public ErrorResponse(int status, String message) {
        this(status, message, LocalDateTime.now());
    }
}
