package com.ecommerce.main.address.service;

import com.ecommerce.main.address.dto.AddressRequest;
import com.ecommerce.main.address.dto.AddressResponse;

import java.util.List;

public interface AddressService {

    List<AddressResponse> findAll(Integer userId);

    AddressResponse findOne(Integer userId, Integer id);

    AddressResponse create(Integer userId, AddressRequest request);

    AddressResponse update(Integer userId, Integer id, AddressRequest request);

    void delete(Long userId, Integer id);
}