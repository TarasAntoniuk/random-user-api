package com.tarasantoniuk.random_user_api.feature.user.controller;

import com.tarasantoniuk.random_user_api.common.dto.ErrorResponse;
import com.tarasantoniuk.random_user_api.feature.user.dto.UserResponseDto;
import com.tarasantoniuk.random_user_api.feature.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Users", description = "Random user generation endpoints")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Get random users", description = "Fetches random users from external API")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @ApiResponse(responseCode = "4xx", description = "External API client error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "503", description = "External API unavailable",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<UserResponseDto> getUsers(
            @RequestParam(defaultValue = "50") @Min(1) int count
    ) {
        UserResponseDto users = userService.getUsers(count);
        return ResponseEntity.ok().body(users);

    }
}
