package com.ecommerce.main.book.service.impl;

import com.ecommerce.main.book.dto.BookResponse;
import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.dto.UpdateBookRequest;
import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.book.mapper.BookMapper;
import com.ecommerce.main.book.repository.BookRepository;
import com.ecommerce.main.book.service.BookService;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.category.repository.CategoryRepository;
import com.ecommerce.main.subcategory.entity.SubCategory;
import com.ecommerce.main.subcategory.repository.SubCategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;


    @Override
    @Transactional
    public Book createBook(CreateBookRequest request) {
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new RuntimeException("دسته بندی موجود نیست"));
        SubCategory subcategory = subCategoryRepository.findById(request.subcategoryId()).orElseThrow(() -> new RuntimeException("زیر گروه موجود نیست"));
        Book entity = bookMapper.toCreateEntity(request, category, subcategory);
        return bookRepository.save(entity);
    }

    @Override
    @Transactional
    public Book updateBook(UpdateBookRequest request) {
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new RuntimeException("دسته بندی موجود نیست"));
        SubCategory subcategory = subCategoryRepository.findById(request.subcategoryId()).orElseThrow(() -> new RuntimeException("زیر گروه موجود نیست"));
        Book book = bookRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("کتاب موجود نیست"));
        Book entity = bookMapper.toUpdateEntity(book, request, category, subcategory);
        return bookRepository.save(entity);

    }

    @Override
    public void deActivateBook(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("کتاب موجود نیست"));
        book.setStatus(false);
        bookRepository.save(book);

    }

    @Override
    public void activateBook(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("کتاب موجود نیست"));
        book.setStatus(true);
        bookRepository.save(book);

    }



}
