package com.alnicode.funvirtualreading.domain.service.impl;

import com.alnicode.funvirtualreading.domain.dto.BookRequest;
import com.alnicode.funvirtualreading.domain.dto.BookResponse;
import com.alnicode.funvirtualreading.domain.service.IBookService;
import com.alnicode.funvirtualreading.exception.RegisterNotValidException;
import com.alnicode.funvirtualreading.persistence.entity.Book;
import com.alnicode.funvirtualreading.persistence.mapper.BookMapper;
import com.alnicode.funvirtualreading.persistence.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.alnicode.funvirtualreading.constants.BookConstants.BODY_EXISTS;
import static com.alnicode.funvirtualreading.constants.BookConstants.SYNOPSIS_EXISTS;
import static com.alnicode.funvirtualreading.constants.BookConstants.TITLE_EXISTS;

/**
 * The book service implementation.
 *
 * @author Alben Bustamante
 * @version 1.0
 * @since 1.0
 */
@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    private BookMapper mapper;

    @Autowired
    private BookRepository repository;

    @Override
    @Transactional
    public BookResponse create(BookRequest request) throws RegisterNotValidException {
        checkData(request);

        return this.mapper.toResponse(this.repository.save(this.mapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAll() {
        return this.mapper.toResponses(this.repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookResponse> get(long id) {
        return this.repository.findById(id).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Optional<BookResponse> update(long id, BookRequest request) {
        var book = this.repository.findById(id);

        if (book.isEmpty()) {
            return Optional.empty();
        }

        var entity = this.mapper.toEntity(request);
        entity.setBookId(id);
        entity.setDate(book.get().getDate());

        return Optional.of(this.mapper.toResponse(this.repository.save(entity)));
    }

    @Override
    public CrudRepository<Book, Long> repository() {
        return this.repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<BookResponse>> getByAuthorId(long userId) {
        return this.repository.findByUserId(userId).map(mapper::toResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<BookResponse>> getByGenre(long genreId) {
        return this.repository.findByGenreId(genreId).map(mapper::toResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookResponse> getByComment(long commentId) {
        return this.repository.findByCommentsId(commentId).map(mapper::toResponse);
    }

    private void checkData(BookRequest request) throws RegisterNotValidException {
        if (repository.existsByTitle(request.getTitle())) {
            throw new RegisterNotValidException(TITLE_EXISTS, "title");
        }

        if (repository.existsBySynopsis(request.getSynopsis())) {
            throw new RegisterNotValidException(SYNOPSIS_EXISTS, "synopsis");
        }

        if (repository.existsByBody(request.getBody())) {
            throw new RegisterNotValidException(BODY_EXISTS, "body");
        }
    }

}
