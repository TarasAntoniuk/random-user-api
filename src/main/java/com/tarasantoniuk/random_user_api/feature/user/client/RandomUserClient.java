package com.tarasantoniuk.random_user_api.feature.user.client;

import com.tarasantoniuk.random_user_api.common.exception.ExternalApiUnavailableException;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import com.tarasantoniuk.random_user_api.feature.user.exception.ExternalApiException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.time.Duration;


@Component
public class RandomUserClient {

    private final RestClient restClient;

    private final static Logger log = LoggerFactory.getLogger(RandomUserClient.class);

    public RandomUserClient(RestClient.Builder builder,
                            @Value("${randomuser.base-url}") String baseUrl,

                            @Value("${randomuser.connect-timeout}") int connectTimeout,
                            @Value("${randomuser.read-timeout}") int readTimeout) {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(connectTimeout));
        requestFactory.setReadTimeout(Duration.ofSeconds(readTimeout));

        this.restClient = builder
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .build();

    }

    @Retry(name = "randomUserApi")
    @CircuitBreaker(name = "randomUserApi", fallbackMethod = "getUsersFallback")
    public UserResponseDto getUsers(int count) {
        try {
            return restClient.get()
                    .uri("/api/?results={count}", count)
                    .retrieve()
                    .body(UserResponseDto.class);
        } catch (HttpClientErrorException ex) {
            log.error("External API client error: {}",  ex.getMessage());
            throw new ExternalApiException(ex.getStatusCode().value(), "External API client error");
        } catch (HttpServerErrorException ex) {
            log.error("External API server error: {}",  ex.getMessage());
            throw new ExternalApiException(502, "External API server error");
        } catch (ResourceAccessException ex) {
            log.error("External API is unavailable: {}",  ex.getMessage());
            throw new ExternalApiException(503, "External API is unavailable");
        }

    }

    private UserResponseDto getUsersFallback(int count, Exception ex) {
        log.warn("External API unavailable: {}", ex.getMessage());
        throw new ExternalApiUnavailableException("Random User API is currently unavailable");
    }

}
