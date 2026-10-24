package com.ecommerce.main.address.service.impl;

import com.ecommerce.main.address.dto.AddressRequest;
import com.ecommerce.main.address.dto.AddressResponse;
import com.ecommerce.main.address.entity.Address;
import com.ecommerce.main.address.mapper.AddressMapper;
import com.ecommerce.main.address.respositroy.AddressRepository;
import com.ecommerce.main.common.exception.AddressNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl {

    private final AddressRepository repository;
    private final AddressMapper mapper;

    @Transactional(readOnly = true)
    public List<AddressResponse> findAll(Long userId) {
        return mapper.toResponses(repository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @Transactional(readOnly = true)
    public AddressResponse findOne(Long userId, Long id) {
        return mapper.toResponse(getOwned(userId, id));
    }

    public AddressResponse create(Long userId, AddressRequest request) {
        Address address = mapper.toEntity(userId, request);
        return mapper.toResponse(repository.save(address));
    }

    public AddressResponse update(Long userId, Long id, AddressRequest request) {
        Address address = getOwned(userId, id);
        mapper.updateEntity(request, address);
        return mapper.toResponse(address); // saved by dirty checking
    }

    public void delete(Long userId, Long id) {
        repository.delete(getOwned(userId, id));
    }

    // Filtering by userId prevents users from accessing others' addresses.
    private Address getOwned(Long userId, Long id) {
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(AddressNotFoundException::new);
    }
}
