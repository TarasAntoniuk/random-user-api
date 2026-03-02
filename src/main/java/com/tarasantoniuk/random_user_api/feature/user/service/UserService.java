package com.tarasantoniuk.random_user_api.feature.user.service;

import com.tarasantoniuk.random_user_api.feature.user.client.RandomUserClient;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RandomUserClient randomUserClient;

    public UserService(RandomUserClient randomUserClient) {
        this.randomUserClient = randomUserClient;
    }

    public UserResponseDto getUsers(int count) {
        return randomUserClient.getUsers(count);
    }
}
