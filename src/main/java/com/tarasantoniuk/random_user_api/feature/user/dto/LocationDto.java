package com.tarasantoniuk.random_user_api.feature.user.dto;

public record LocationDto(
        StreetDto street,
        String city,
        String state,
        String country,
        String postcode,
        CoordinatesDto coordinates,
        TimezoneDto timezone
) {}