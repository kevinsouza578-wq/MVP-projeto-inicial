package br.com.mvp.educagames.dto;

public record RankingEntryResponse(
        Integer position,
        String username,
        Integer totalScore,
        Integer level
) {
}
