package com.ecommerce.main.book.service.impl;

import com.ecommerce.main.book.dto.*;
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

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

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
        Book book = bookRepository.findByIdLocked(request.id()).orElseThrow(() -> new RuntimeException("کتاب موجود نیست"));
        Book entity = bookMapper.toUpdateEntity(book, request, category, subcategory);
        return bookRepository.save(entity);

    }

    @Override
    @Transactional
    public String uploadImage(MultipartFile file, Long id) {
        try {

            Book book = bookRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("کتاب مورد نظر یافت نشد"));
            String url = fileStorageService.save(file, "book");
            book.setCoverImageUrl(url);
            return url;
        } catch (IOException e) {
            throw new FileStorageException("دخیره سازی با خطا مواجه شد");

        }
    }

    private static final Map<String, String> SORT_FIELDS = Map.of(
            "price", "price",
            "newest", "createdAt",
            "title", "title");

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BookListItem> search(BookFilter f, int page, int size,
                                             String sortBy, String direction) {

        int safeSize = Math.min(Math.max(size, 1), 50);        // never more than 50
        int safePage = Math.max(page, 0);

        String field = SORT_FIELDS.getOrDefault(sortBy, "createdAt");   // only allowed fields
        Sort.Direction dir = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(dir, field).and(Sort.by(Sort.Direction.ASC, "id"));   // stable order

        Specification<Book> spec = Specification.allOf(
                BookSpecifications.isActive(),
                BookSpecifications.hasCategory(f.categoryId()),
                BookSpecifications.hasSubCategory(f.subCategoryId()),
                BookSpecifications.priceAtLeast(f.minPrice()),
                BookSpecifications.priceAtMost(f.maxPrice()),
                BookSpecifications.titleContains(f.search()),
                BookSpecifications.inStock(f.inStock()),
                BookSpecifications.hasLanguage(f.language()));

        Page<Book> result = bookRepository.findAll(spec, PageRequest.of(safePage, safeSize, sort));
        return PageResponse.from(result.map(bookMapper::toListItem));
    }


}
