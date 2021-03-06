package com.alnicode.funvirtualreading.domain.service.impl;

import com.alnicode.funvirtualreading.domain.dto.GenreRequest;
import com.alnicode.funvirtualreading.domain.dto.GenreResponse;
import com.alnicode.funvirtualreading.domain.service.IGenreService;
import com.alnicode.funvirtualreading.exception.RegisterNotValidException;
import com.alnicode.funvirtualreading.persistence.entity.Genre;
import com.alnicode.funvirtualreading.persistence.mapper.GenreMapper;
import com.alnicode.funvirtualreading.persistence.repository.GenreRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.alnicode.funvirtualreading.constants.GenreConstants.DESCRIPTION_EXISTS;
import static com.alnicode.funvirtualreading.constants.GenreConstants.NAME_EXISTS;

/**
 * The genre service implementation.
 *
 * @author Alben Bustamante
 * @version 1.0
 * @since 1.0
 */
@Service
public class GenreServiceImpl implements IGenreService {
    @Autowired
    private GenreMapper mapper;

    @Autowired
    private GenreRepository repository;

    @Override
    @Transactional
    public GenreResponse create(GenreRequest request) throws RegisterNotValidException {
        checkData(request);

        return this.mapper.toResponse(this.repository.save(this.mapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreResponse> getAll() {
        return this.mapper.toResponses(this.repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreResponse> get(long id) {
        return this.repository.findById(id).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Optional<GenreResponse> update(long id, GenreRequest request) {
        if (!this.repository.existsById(id)) {
            return Optional.empty();
        }

        var entity = this.mapper.toEntity(request);
        entity.setId(id);

        return Optional.of(this.mapper.toResponse(this.repository.save(entity)));
    }

    @Override
    public CrudRepository<Genre, Long> repository() {
        return this.repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreResponse> getByBookId(long bookId) {
        return this.repository.findByBooksBookId(bookId).map(mapper::toResponse);
    }

    private void checkData(GenreRequest request) throws RegisterNotValidException {
        if (repository.existsByName(request.getName())) {
            throw new RegisterNotValidException(NAME_EXISTS, "name");
        }

        if (repository.existsByDescription(request.getDescription())) {
            throw new RegisterNotValidException(DESCRIPTION_EXISTS, "description");
        }
    }

}
