package com.tarasantoniuk.random_user_api.feature.user.controller;

import com.tarasantoniuk.random_user_api.common.exception.ExternalApiUnavailableException;
import com.tarasantoniuk.random_user_api.feature.user.controller.UserController;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import com.tarasantoniuk.random_user_api.feature.user.exception.ExternalApiException;
import com.tarasantoniuk.random_user_api.feature.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void getUsers_defaultCount_returns200() throws Exception {
        when(userService.getUsers(50))
                .thenReturn(new UserResponseDto(List.of(), null));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    void getUsers_customCount_returns200() throws Exception {
        when(userService.getUsers(10))
                .thenReturn(new UserResponseDto(List.of(), null));

        mockMvc.perform(get("/api/users").param("count", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    void getUsers_externalApiError_returns502() throws Exception {
        when(userService.getUsers(50))
                .thenThrow(new ExternalApiException(502, "External API server error"));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.message").value("External API error"));
    }

    @Test
    void getUsers_serviceUnavailable_returns503() throws Exception {
        when(userService.getUsers(50))
                .thenThrow(new ExternalApiUnavailableException("API unavailable"));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("External API is currently unavailable"));
    }

    @Test
    void getUsers_countZero_validationRejects() throws Exception {
        mockMvc.perform(get("/api/users").param("count", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request parameter"));
    }

    @Test
    void getUsers_countExceedsMax_serviceRejects() throws Exception {
        when(userService.getUsers(5001))
                .thenThrow(new IllegalArgumentException("count must be between 1 and 5000"));

        mockMvc.perform(get("/api/users").param("count", "5001"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("count must be between 1 and 5000"));
    }
}