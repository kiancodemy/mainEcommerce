package com.ecommerce.main.book.controller;

import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.book.service.impl.BookServiceImpl;
import com.ecommerce.main.common.components.FileStorageService;
import com.ecommerce.main.common.dto.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/admin/books/")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminBookController {
    private final BookServiceImpl bookService;


    @PostMapping("createNewBook")
    public ResponseEntity<ApiResponse> createNewBook(@Valid @RequestBody CreateBookRequest request) {
        Book book = bookService.createBook(request);
        bookService.createBook(request);
        return ResponseEntity.ok().body(new ApiResponse("محصول جدید ایجاد شد", book));

    }

    @PostMapping("updateBook")
    public ResponseEntity<ApiResponse> updateBook(@Valid @RequestBody CreateBookRequest request) {
        Book book = bookService.createBook(request);
        bookService.createBook(request);
        return ResponseEntity.ok().body(new ApiResponse("محصول جدید ایجاد شد", book));

    }

    @PutMapping("deActivateBook/{id}")
    public ResponseEntity<ApiResponse> deActivateBook(@PathVariable("id") @Positive(message = "شناسه نباید منفی باشد") Integer id) {
        bookService.deActivateBook(id);
        return ResponseEntity.ok().body(new ApiResponse("محصول غیر فعال شد", null));


    }

    @PostMapping("deActivateBook")
    public ResponseEntity<ApiResponse> activateBook(@PathVariable("id") @Positive(message = "شناسه نباید منفی باشد") Integer id) {
        bookService.activateBook(id);
        return ResponseEntity.ok().body(new ApiResponse("محصول فعال شد", null));

    }




}
