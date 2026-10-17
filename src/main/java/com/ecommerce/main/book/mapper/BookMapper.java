package com.ecommerce.main.book.mapper;

import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.dto.UpdateBookRequest;
import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.subcategory.entity.SubCategory;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toCreateEntity(CreateBookRequest req, Category category, SubCategory subCategory) {
        return Book.builder()
                .title(req.title())
                .description(req.description())
                .publisher(req.publisher())
                .translator(req.translator())
                .language(req.language())
                .publicationYear(req.publicationYear())
                .pages(req.pages())
                .price(req.price())
                .costPrice(req.costPrice())
                .stock(req.stock())
                .coverImageUrl(req.coverImageUrl())
                .category(category)
                .subCategory(subCategory)
                .build();
    }

    public Book toUpdateEntity(Book book,UpdateBookRequest req, Category category, SubCategory subCategory) {
        return book.builder()
                .title(req.title())
                .description(req.description())
                .publisher(req.publisher())
                .translator(req.translator())
                .status((req.status()))
                .language(req.language())
                .publicationYear(req.publicationYear())
                .pages(req.pages())
                .price(req.price())
                .costPrice(req.costPrice())
                .stock(req.stock())
                .coverImageUrl(req.coverImageUrl())
                .category(category)
                .subCategory(subCategory)
                .build();
    }
}
