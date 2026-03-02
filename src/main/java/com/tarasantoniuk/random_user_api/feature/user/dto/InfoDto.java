package com.tarasantoniuk.random_user_api.feature.user.dto;
public record InfoDto(
        String seed,
        int results,
        int page,
        String version
) {
}