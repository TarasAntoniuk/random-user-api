package com.tarasantoniuk.random_user_api.common.exception;

import com.tarasantoniuk.random_user_api.common.dto.ErrorResponse;
import com.tarasantoniuk.random_user_api.feature.user.exception.ExternalApiException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleExternalApiException_returns502() {
        ExternalApiException ex = new ExternalApiException(502, "server error");

        ResponseEntity<ErrorResponse> response = handler.handleExternalApiException(ex);

        assertEquals(502, response.getStatusCode().value());
        assertEquals("External API error", response.getBody().message());
    }

    @Test
    void handleExternalApiException_unknownStatusCode_returns502() {
        ExternalApiException ex = new ExternalApiException(999, "weird error");

        ResponseEntity<ErrorResponse> response = handler.handleExternalApiException(ex);

        assertEquals(502, response.getStatusCode().value());
    }

    @Test
    void handleExternalApiUnavailable_returns503() {
        ExternalApiUnavailableException ex =
                new ExternalApiUnavailableException("unavailable");

        ResponseEntity<ErrorResponse> response = handler.handleExternalApiUnavailable(ex);

        assertEquals(503, response.getStatusCode().value());
        assertEquals("External API is currently unavailable", response.getBody().message());
    }

    @Test
    void handleIllegalArgument_returns400() {
        IllegalArgumentException ex =
                new IllegalArgumentException("count must be between 1 and 5000");

        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgument(ex);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void handleGenericException_returns500() {
        Exception ex = new Exception("something broke");

        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Internal server error", response.getBody().message());
    }
}