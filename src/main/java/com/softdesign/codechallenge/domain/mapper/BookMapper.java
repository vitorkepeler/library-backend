package com.softdesign.codechallenge.domain.mapper;

import com.softdesign.codechallenge.dataaccess.entity.BookEntity;
import com.softdesign.codechallenge.domain.dto.BookRequestDTO;
import com.softdesign.codechallenge.domain.dto.BookResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookResponseDTO toDTO(BookEntity bookEntity);

    BookEntity toEntity(BookRequestDTO bookRequestDTO);
}
