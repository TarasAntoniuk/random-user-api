package com.tarasantoniuk.random_user_api.feature.user.service;


import com.tarasantoniuk.random_user_api.feature.user.client.RandomUserClient;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private RandomUserClient randomUserClient;

    private final int maxCount = 5000;

    private UserService userService;

    @Test
    void getUsers_validCount_delegatesToClient() {
        userService = new UserService(randomUserClient, maxCount);
        UserResponseDto expected = new UserResponseDto(List.of(), null);
        when(randomUserClient.getUsers(10)).thenReturn(expected);

        UserResponseDto result = userService.getUsers(10);

        assertEquals(expected, result);
        verify(randomUserClient).getUsers(10);
    }

    @Test
    void getUsers_countZero_throwsIllegalArgument() {
        userService = new UserService(randomUserClient, maxCount);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUsers(0)
        );

        assertTrue(ex.getMessage().contains("between 1 and 5000"));
        verifyNoInteractions(randomUserClient);
    }

    @Test
    void getUsers_countNegative_throwsIllegalArgument() {
        userService = new UserService(randomUserClient, maxCount);

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUsers(-1)
        );

        verifyNoInteractions(randomUserClient);
    }

    @Test
    void getUsers_countExceedsMax_throwsIllegalArgument() {
        userService = new UserService(randomUserClient, maxCount);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUsers(5001)
        );

        assertTrue(ex.getMessage().contains("between 1 and 5000"));
        verifyNoInteractions(randomUserClient);
    }

    @Test
    void getUsers_countExactlyMax_delegatesToClient() {
        userService = new UserService(randomUserClient, maxCount);
        UserResponseDto expected = new UserResponseDto(List.of(), null);
        when(randomUserClient.getUsers(5000)).thenReturn(expected);

        UserResponseDto result = userService.getUsers(5000);

        assertEquals(expected, result);
    }

    @Test
    void getUsers_countExactlyOne_delegatesToClient() {
        userService = new UserService(randomUserClient, maxCount);
        UserResponseDto expected = new UserResponseDto(List.of(), null);
        when(randomUserClient.getUsers(1)).thenReturn(expected);

        UserResponseDto result = userService.getUsers(1);

        assertEquals(expected, result);
    }
}