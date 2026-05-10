package br.com.mvp.educagames.dto;

import java.time.Instant;

public record UserResponse(
        Long id,
        String name,
        String username,
        String email,
        String photoUrl,
        Integer totalScore,
        Integer level,
        Instant createdAt
) {
}
