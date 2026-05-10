package br.com.mvp.educagames.dto;

import java.time.Instant;

public record ScoreResponse(
        Long id,
        String gameSlug,
        Integer score,
        Integer totalScore,
        Integer level,
        Instant playedAt
) {
}
