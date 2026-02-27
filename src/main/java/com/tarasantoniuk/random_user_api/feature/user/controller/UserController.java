package com.tarasantoniuk.random_user_api.feature.user.controller;

import com.tarasantoniuk.random_user_api.feature.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        String users = userService.getUsers();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(users);
    }
}
