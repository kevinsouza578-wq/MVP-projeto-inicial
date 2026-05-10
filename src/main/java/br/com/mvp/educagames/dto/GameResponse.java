package br.com.mvp.educagames.dto;

import br.com.mvp.educagames.entity.Difficulty;

public record GameResponse(
        Long id,
        String title,
        String slug,
        String description,
        String category,
        Difficulty difficulty,
        String coverImageUrl,
        String pathUrl,
        Integer maxScore,
        Boolean active
) {
}
