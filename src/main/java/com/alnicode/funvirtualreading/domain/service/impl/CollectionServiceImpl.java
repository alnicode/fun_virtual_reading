package com.alnicode.funvirtualreading.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.alnicode.funvirtualreading.domain.dto.CollectionRequest;
import com.alnicode.funvirtualreading.domain.dto.CollectionResponse;
import com.alnicode.funvirtualreading.domain.service.ICollectionService;
import com.alnicode.funvirtualreading.persistence.entity.Collection;
import com.alnicode.funvirtualreading.persistence.mapper.CollectionMapper;
import com.alnicode.funvirtualreading.persistence.repository.CollectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionServiceImpl extends DeleteService<Collection> implements ICollectionService {
    @Autowired
    private CollectionMapper mapper;

    @Autowired
    private CollectionRepository repository;

    @Override
    @Transactional
    public CollectionResponse save(CollectionRequest request) {
        return this.mapper.toResponse(this.repository.save(this.mapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollectionResponse> getAll() {
        return this.mapper.toResponses(this.repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CollectionResponse> get(long id) {
        return this.repository.findById(id).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Optional<CollectionResponse> update(long id, CollectionRequest request) {
        var collection = this.repository.findById(id);

        if (!collection.isPresent()) {
            return Optional.empty();
        }

        var entity = this.mapper.toEntity(request);
        entity.setCollectionId(id);
        entity.setDate(collection.get().getDate());

        return Optional.of(this.mapper.toResponse(this.repository.save(entity)));
    }

    @Override
    protected CrudRepository<Collection, Long> repository() {
        return this.repository;
    }
    
}