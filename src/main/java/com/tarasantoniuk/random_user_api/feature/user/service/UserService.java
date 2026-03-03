package com.tarasantoniuk.random_user_api.feature.user.service;

import com.tarasantoniuk.random_user_api.feature.user.client.RandomUserClient;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RandomUserClient randomUserClient;
    /**
     * Maximum number of users per single request to randomuser.me API.
     * <p>
     * TODO: Validate default value (5000) based on:
     * - Actual request patterns and typical user demands
     * - API rate limits and response times
     * - System resource constraints (memory, database)
     */
    private final int maxCount;

    public UserService(RandomUserClient randomUserClient
            , @Value("${randomuser.max-count:5000}") int maxCount) {

        this.randomUserClient = randomUserClient;
        this.maxCount = maxCount;
    }

    public UserResponseDto getUsers(int count) {
        if (count < 1 || count > maxCount) {
            throw new IllegalArgumentException("count must be between 1 and " + maxCount);
        }
        return randomUserClient.getUsers(count);
    }
}
