package com.ecommerce.main.address.mapper;

import com.ecommerce.main.address.dto.AddressRequest;
import com.ecommerce.main.address.dto.AddressResponse;
import com.ecommerce.main.address.entity.Address;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressMapper {

    public AddressResponse toResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getProvince(),
                address.getCity(),
                address.getPostalCode(),
                address.getPhoneNumber(),
                address.getStaticNumber(),
                address.getDescription(),
                address.getCreatedAt(),
                address.getUpdatedAt());
    }

    public List<AddressResponse> toResponses(List<Address> addresses) {
        return addresses.stream().map(this::toResponse).toList();
    }

    public void updateEntity(AddressRequest request, Address address) {
        address.setProvince(request.province());
        address.setCity(request.city());
        address.setPostalCode(request.postalCode());
        address.setPhoneNumber(request.phoneNumber());
        address.setStaticNumber(request.staticNumber());
        address.setDescription(request.description());
    }
    public Address toEntity(Integer userId, AddressRequest request) {
        return Address.builder()
                .userId(userId)
                .province(request.province())
                .city(request.city())
                .postalCode(request.postalCode())
                .phoneNumber(request.phoneNumber())
                .staticNumber(request.staticNumber())
                .description(request.description())
                .build();
    }


}
