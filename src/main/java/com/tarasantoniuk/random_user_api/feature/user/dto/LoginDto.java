package com.tarasantoniuk.random_user_api.feature.user.dto;

public record LoginDto(
        String uuid,
        String username,
        String password,
        String salt,
        String md5,
        String sha1,
        String sha256
) {}