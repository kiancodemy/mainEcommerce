package com.ecommerce.main.book.service;

import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.dto.UpdateBookRequest;
import com.ecommerce.main.book.entity.Book;

public interface BookService {
    Book createBook(CreateBookRequest request);
    Book updateBook( UpdateBookRequest request);
    void deActivateBook(Integer id);
    void activateBook(Integer id);


}
