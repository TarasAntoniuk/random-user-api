package com.tarasantoniuk.random_user_api.common.exception;

public class ExternalApiUnavailableException extends RuntimeException {
    public ExternalApiUnavailableException(String message) {
        super(message);
    }
}