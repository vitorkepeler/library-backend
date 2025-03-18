package com.softdesign.codechallenge.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BookRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String synopsis,
        @NotBlank
        String author,
        @NotBlank
        String category,
        @NotBlank
        String publicationYear
) {
}
