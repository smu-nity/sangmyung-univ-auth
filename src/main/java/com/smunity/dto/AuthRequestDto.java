package com.smunity.dto;

import lombok.Builder;

@Builder
public record AuthRequestDto(
        String username,

        String password
) {

    public static AuthRequestDto of(String username, String password) {
        return AuthRequestDto.builder()
                .username(username)
                .password(password)
                .build();
    }
}
