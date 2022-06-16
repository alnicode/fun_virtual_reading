package com.alnicode.funvirtualreading.persistence.mapper;

import com.alnicode.funvirtualreading.domain.dto.PersonRequest;
import com.alnicode.funvirtualreading.domain.dto.PersonResponse;
import com.alnicode.funvirtualreading.persistence.entity.Person;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import static com.alnicode.funvirtualreading.util.AppConstants.DATE_FORMAT;
import static com.alnicode.funvirtualreading.util.AppConstants.DATE_TIME_FORMAT;

/**
 * The person mapper.
 *
 * @author Alben Bustamante
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring", uses = {LikeMapper.class})
public interface PersonMapper {

    /**
     * Map a person entity to the person response DTO.
     *
     * @param entity the {@link Person} entity to be mapped
     * @return the {@link PersonResponse} DTO
     */
    @Mapping(target = "birthday", dateFormat = DATE_FORMAT)
    @Mapping(target = "date", dateFormat = DATE_TIME_FORMAT)
    @Mapping(target = "nationality", source = "nationality.name")
    PersonResponse toResponse(Person entity);

    /**
     * Map an entities list to the responses list.
     *
     * @param entities the list to be mapped
     * @return the responses list
     */
    List<PersonResponse> toResponses(List<Person> entities);

    /**
     * Map a person request DTO to the person entity.
     *
     * @param request the {@link PersonRequest} DTO to be mapped
     * @return the {@link Person} entity
     */
    @Mapping(target = "birthday", dateFormat = DATE_FORMAT)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nationality", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "publishedBooks", ignore = true)
    @Mapping(target = "publishedComments", ignore = true)
    @Mapping(target = "collections", ignore = true)
    @Mapping(target = "likes", ignore = true)
    Person toEntity(PersonRequest request);
}
