package com.ecommerce.main.book.service;

import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.dto.UpdateBookRequest;
import com.ecommerce.main.book.entity.Book;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Book createBook(CreateBookRequest request);
    Book updateBook( UpdateBookRequest request);
    String uploadImage(MultipartFile file,Integer id);



}
