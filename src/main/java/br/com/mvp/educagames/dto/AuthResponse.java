package br.com.mvp.educagames.dto;

public record AuthResponse(
        String token,
        String tokenType,
        UserResponse user
) {
}
