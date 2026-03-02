package com.tarasantoniuk.random_user_api.feature.user.dto;

import java.util.List;

public record UserResponseDto(
        List<UserDto> results,
        InfoDto info
) {}