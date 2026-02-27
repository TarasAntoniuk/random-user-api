package com.tarasantoniuk.random_user_api.feature.user.service;

import com.tarasantoniuk.random_user_api.feature.user.client.RandomUserClient;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RandomUserClient randomUserClient;

    public UserService(RandomUserClient randomUserClient) {
        this.randomUserClient = randomUserClient;
    }

    public String getUsers() {
        return randomUserClient.getUsers();
    }
}
