package br.com.mvp.educagames.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScoreSubmitRequest(
        @NotBlank String gameSlug,
        @NotNull @Min(0) @Max(100000) Integer score
) {
}
