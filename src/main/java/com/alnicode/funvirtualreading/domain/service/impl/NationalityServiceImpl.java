package com.alnicode.funvirtualreading.domain.service.impl;

import com.alnicode.funvirtualreading.domain.dto.NationalityRequest;
import com.alnicode.funvirtualreading.domain.dto.NationalityResponse;
import com.alnicode.funvirtualreading.domain.service.INationalityService;
import com.alnicode.funvirtualreading.exception.RegisterNotValidException;
import com.alnicode.funvirtualreading.persistence.entity.Nationality;
import com.alnicode.funvirtualreading.persistence.mapper.NationalityMapper;
import com.alnicode.funvirtualreading.persistence.repository.NationalityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.alnicode.funvirtualreading.constants.NationalityConstants.COUNTRY_EXISTS;
import static com.alnicode.funvirtualreading.constants.NationalityConstants.NAME_EXISTS;

/**
 * The nationality service implementation.
 *
 * @author Alben Bustamante
 * @version 1.0
 * @since 1.0
 */
@Service
public class NationalityServiceImpl implements INationalityService {
    @Autowired
    private NationalityMapper mapper;

    @Autowired
    private NationalityRepository repository;

    @Override
    @Transactional
    public NationalityResponse create(NationalityRequest request) throws RegisterNotValidException {
        checkData(request);

        return this.mapper.toResponse(this.repository.save(this.mapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NationalityResponse> getAll() {
        return this.mapper.toResponses(this.repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NationalityResponse> get(long id) {
        return this.repository.findById(id).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Optional<NationalityResponse> update(long id, NationalityRequest request) {
        if (!this.repository.existsById(id)) {
            return Optional.empty();
        }

        var entity = this.mapper.toEntity(request);
        entity.setId(id);

        return Optional.of(this.mapper.toResponse(entity));
    }

    @Override
    public CrudRepository<Nationality, Long> repository() {
        return this.repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NationalityResponse> getAllOrderByCountry() {
        return this.mapper.toResponses(this.repository.findAllByOrderByCountry());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NationalityResponse> getByCountry(String country) {
        return this.repository.findByCountry(country).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NationalityResponse> getByAuthorId(long userId) {
        return this.repository.findByUsersId(userId).map(mapper::toResponse);
    }

    private void checkData(NationalityRequest request) throws RegisterNotValidException {
        if (repository.existsByName(request.getName())) {
            throw new RegisterNotValidException(NAME_EXISTS, "name");
        }

        if (repository.findByCountry(request.getCountry()).isPresent()) {
            throw new RegisterNotValidException(COUNTRY_EXISTS, "country");
        }
    }

}
