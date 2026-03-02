package com.tarasantoniuk.random_user_api.feature.user.client;

import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import com.tarasantoniuk.random_user_api.feature.user.exception.ExternalApiException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;


@Component
public class RandomUserClient {

    private final RestClient restClient;

    private final static Logger log = LoggerFactory.getLogger(RandomUserClient.class);

    @Value("${randomuser.base-url}")
    private String baseUrl;

    public RandomUserClient(RestClient.Builder builder,
                            @Value("${randomuser.base-url}") String baseUrl) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

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
}
