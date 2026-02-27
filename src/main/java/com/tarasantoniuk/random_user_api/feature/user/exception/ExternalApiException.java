package com.tarasantoniuk.random_user_api.feature.user.exception;

import java.time.LocalDateTime;

public class ExternalApiException extends RuntimeException {
    private final int statusCode;

    public ExternalApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
