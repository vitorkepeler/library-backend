package com.softdesign.codechallenge.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GetAllBookResponseDTO (
        List<BookResponseDTO> books,
        Integer totalPages
){}
