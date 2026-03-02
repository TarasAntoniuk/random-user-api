package com.tarasantoniuk.random_user_api.feature.user.dto;
public record UserDto(
        String gender,
        NameDto name,
        LocationDto location,
        String email,
        LoginDto login,
        DobDto dob,
        RegisteredDto registered,
        String phone,
        String cell,
        IdDto id,
        PictureDto picture,
        String nat
) {}