package com.tarasantoniuk.random_user_api.feature.user.service;

import com.tarasantoniuk.random_user_api.feature.user.client.RandomUserClient;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserDataDto;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserDto;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, List<UserDataDto>>  getGenderData(int count) {
        if (count < 1 || count > maxCount) {
            throw new IllegalArgumentException("count must be between 1 and " + maxCount);
        }

        List<UserDto> originalUsers = randomUserClient.getUsers(count).results();

        List<UserDataDto> male = originalUsers.stream()
                .filter(userDto -> userDto.gender().equalsIgnoreCase("male"))
                .map(userDto -> new UserDataDto(userDto.email(),  userDto.location().country()))
                .toList();

        List<UserDataDto> female = originalUsers.stream()
                .filter(userDto -> userDto.gender().equalsIgnoreCase("female"))
                .map(userDto -> new UserDataDto(userDto.email(),  userDto.location().country()))
                .toList();

        Map<String, List<UserDataDto>> genderData = new HashMap<>();

        genderData.put("male", male);
        genderData.put("female", female);


        return genderData;
    }

}
