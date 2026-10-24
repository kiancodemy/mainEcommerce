package com.ecommerce.main.address.controller;

import com.ecommerce.main.address.dto.AddressRequest;
import com.ecommerce.main.address.dto.AddressResponse;
import com.ecommerce.main.address.service.impl.AddressServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressServiceImpl service;

    // Assumes your authenticated principal exposes getId() returning the user's Integer id.
    @GetMapping
    public ResponseEntity<ApiResponse> findAll(@AuthenticationPrincipal(expression = "id") Long userId) {
        List<AddressResponse> all = service.findAll(userId);
        return ResponseEntity.ok().body(new ApiResponse("success", all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findOne(@AuthenticationPrincipal(expression = "id") Long userId,
                                               @PathVariable Long id) {
        AddressResponse findAddress = service.findOne(userId, id);
        return ResponseEntity.ok().body(new ApiResponse("success", findAddress));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@AuthenticationPrincipal(expression = "id") Long userId,
                                              @Valid @RequestBody AddressRequest request) {
        AddressResponse updatedAddress = service.create(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", updatedAddress));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@AuthenticationPrincipal(expression = "id") Long userId,
                                              @PathVariable Long id,
                                              @Valid @RequestBody AddressRequest request) {
        AddressResponse update = service.update(userId, id, request);
        return ResponseEntity.ok().body(new ApiResponse("success", update));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@AuthenticationPrincipal(expression = "id") Long userId,
                                              @PathVariable Long id) {
        service.delete(userId, id);
        return ResponseEntity.ok().body(new ApiResponse("success", null));

    }
}