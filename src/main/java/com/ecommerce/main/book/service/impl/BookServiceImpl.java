package com.ecommerce.main.book.service.impl;

import com.ecommerce.main.book.dto.BookResponse;
import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.dto.UpdateBookRequest;
import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.book.repository.BookRepository;
import com.ecommerce.main.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book createBook(@Valid @RequestBody CreateBookRequest request) {

    }

    @Override
    public Book updateBook(UpdateBookRequest request) {
        return null;
    }
}
