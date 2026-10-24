package com.ecommerce.main.book.service.impl;

import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.dto.UpdateBookRequest;
import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.book.mapper.BookMapper;
import com.ecommerce.main.book.repository.BookRepository;
import com.ecommerce.main.book.service.BookService;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.category.repository.CategoryRepository;
import com.ecommerce.main.common.components.FileStorageService;
import com.ecommerce.main.common.exception.FileStorageException;
import com.ecommerce.main.subcategory.entity.SubCategory;
import com.ecommerce.main.subcategory.repository.SubCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final FileStorageService fileStorageService;


    @Override

    public Book createBook(CreateBookRequest request) {
        SubCategory subcategory = subCategoryRepository.findById(request.subcategoryId()).orElseThrow(() -> new RuntimeException("زیر گروه موجود نیست"));

        if (!subcategory.getCategory().getId().equals(request.categoryId())) {
            throw new UsernameNotFoundException("ریز دسته با دسته تطابق ندارد");
        }

        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new RuntimeException("دسته بندی موجود نیست"));
        Book entity = bookMapper.toCreateEntity(request, category, subcategory);
        return bookRepository.save(entity);
    }

    @Override

    public Book updateBook(UpdateBookRequest request) {

        SubCategory subcategory = subCategoryRepository.findById(request.subcategoryId()).orElseThrow(() -> new RuntimeException("زیر گروه موجود نیست"));

        if (!subcategory.getCategory().getId().equals(request.categoryId())) {
            throw new UsernameNotFoundException("ریز دسته با دسته تطابق ندارد");
        }
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new RuntimeException("دسته بندی موجود نیست"));
        Book book = bookRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("کتاب موجود نیست"));
        Book entity = bookMapper.toUpdateEntity(book, request, category, subcategory);
        return bookRepository.save(entity);

    }

    @Override
    @Transactional
    public String uploadImage(MultipartFile file, Long  id) {
        try {

            Book book = bookRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("کتاب مورد نظر یافت نشد"));
            String url = fileStorageService.save(file, "book");
            book.setCoverImageUrl(url);
            return url;
        }
        catch (IOException e) {
            throw new FileStorageException("دخیره سازی با خطا مواجه شد");

        }
    }


}
