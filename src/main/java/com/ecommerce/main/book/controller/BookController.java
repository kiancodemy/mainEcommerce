package com.ecommerce.main.book.controller;

import com.ecommerce.main.book.dto.BookFilter;
import com.ecommerce.main.book.dto.BookListItem;
import com.ecommerce.main.book.dto.PageResponse;
import com.ecommerce.main.book.service.impl.BookServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;
    @PreAuthorize("permitAll()")   // note: capital A, parentheses
    @GetMapping
    public ResponseEntity<ApiResponse> search(BookFilter filter,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size,
                                              @RequestParam(defaultValue = "newest") String sortBy,
                                              @RequestParam(defaultValue = "desc") String direction) {
        PageResponse<BookListItem> search = bookService.search(filter, page, size, sortBy, direction);
        return ResponseEntity.ok()
                .body(new ApiResponse("success",search));

    }
}
