package com.tarasantoniuk.random_user_api.feature.user.client;

import com.tarasantoniuk.random_user_api.common.exception.ExternalApiUnavailableException;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import com.tarasantoniuk.random_user_api.feature.user.exception.ExternalApiException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;


@Component
public class RandomUserClient {

    private final RestClient restClient;

    private static final Logger log = LoggerFactory.getLogger(RandomUserClient.class);

    public RandomUserClient(RestClient restClient) {

        this.restClient = restClient;
    }

    @Retry(name = "randomUserApi")
    @CircuitBreaker(name = "randomUserApi", fallbackMethod = "getUsersFallback")
    public UserResponseDto getUsers(int count) {
        try {
            return restClient.get()
                    .uri("/api/?results={count}", count)
                    .retrieve()
                    .body(UserResponseDto.class);
//      } catch (HttpClientErrorException ex) {
//            log.error("External API client error: {}",  ex.getMessage());
//            throw new ExternalApiException(ex.getStatusCode().value(), "External API client error");
        } catch (HttpServerErrorException ex) {
            log.error("External API server error: {}",  ex.getMessage());
            throw new ExternalApiException(502, "External API server error");
        } catch (ResourceAccessException ex) {
            log.warn("External API is unavailable: {}",  ex.getMessage());
            throw new ExternalApiException(503, "External API is unavailable");
        }

    }

    /**
     * Parameters must match the original method signature + Exception.
     */
    private UserResponseDto getUsersFallback(int count, Exception ex) {
        log.warn("External API unavailable: {}", ex.getMessage());
        throw new ExternalApiUnavailableException("Random User API is currently unavailable");
    }

}
