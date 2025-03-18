package com.softdesign.codechallenge.domain.dto;

import lombok.Builder;

@Builder
public record BookResponseDTO(
    Long id,
    String title,
    String synopsis,
    String author,
    String category,
    String publicationYear,
    Boolean available
) {
}
