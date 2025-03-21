package com.softdesign.codechallenge.domain.dto;

import lombok.Builder;

@Builder
public record LoginResponseDTO(
    String token,
    Long expiresIn
) {
}
