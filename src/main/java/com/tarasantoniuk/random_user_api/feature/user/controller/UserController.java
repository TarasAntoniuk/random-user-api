package com.tarasantoniuk.random_user_api.feature.user.controller;

import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import com.tarasantoniuk.random_user_api.feature.user.service.UserService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponseDto> getUsers(
            @RequestParam(defaultValue = "50") int count
    ) {
        UserResponseDto users = userService.getUsers(count);
        return ResponseEntity.ok().body(users);

    }
}
