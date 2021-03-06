package com.alnicode.funvirtualreading.persistence.mapper;

import com.alnicode.funvirtualreading.domain.dto.GenreRequest;
import com.alnicode.funvirtualreading.domain.dto.GenreResponse;
import com.alnicode.funvirtualreading.persistence.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The genre mapper.
 *
 * @author Alben Bustamante
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface GenreMapper extends BaseMapper<GenreResponse, Genre> {

    /**
     * Map a genre entity to the genre response DTO.
     *
     * @param entity the {@link Genre} entity to be mapped
     * @return the {@link GenreResponse} DTO
     */
    GenreResponse toResponse(Genre entity);

    /**
     * Map a genre request DTO to the genre entity.
     *
     * @param request the {@link GenreRequest} DTO to be mapped
     * @return the {@link Genre} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Genre toEntity(GenreRequest request);
}
