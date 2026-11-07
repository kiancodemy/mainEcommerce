package com.ecommerce.main.book.controller;

import com.ecommerce.main.book.dto.AdminBookResponse;
import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.dto.UpdateBookRequest;
import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.book.mapper.BookMapper;
import com.ecommerce.main.book.service.impl.BookServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/books")
@Tag(name="admin book controller")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminBookController {
    private final BookServiceImpl bookService;
    private final BookMapper bookMapper;


    @PostMapping
    @Operation(summary = "create new book ")
    public ResponseEntity<ApiResponse> createNewBook(@Valid @RequestBody CreateBookRequest request) {
        Book book = bookService.createBook(request);
        AdminBookResponse adminResponse = bookMapper.toAdminResponse(book);

        return ResponseEntity.ok().body(new ApiResponse("محصول جدید ایجاد شد", adminResponse));

    }

    @PutMapping
    @Operation(summary = "update book")
    public ResponseEntity<ApiResponse> updateBook(@Valid @RequestBody UpdateBookRequest request) {
        Book book = bookService.updateBook(request);
        AdminBookResponse adminResponse = bookMapper.toAdminResponse(book);

        return ResponseEntity.ok().body(new ApiResponse("محصول به روز رسانی شد ایجاد شد", adminResponse));

    }

    @Operation(summary = "upload image for book ")
    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<ApiResponse> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {

        String url = bookService.uploadImage(file, id);
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت ذخیره شد", url));
    }
}
