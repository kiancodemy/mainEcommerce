package com.ecommerce.main.book.mapper;

import com.ecommerce.main.book.dto.CreateBookRequest;
import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.subcategory.entity.SubCategory;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(CreateBookRequest req, Category category, SubCategory subCategory) {
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
                .SubCategory(subCategory)
                // NOTE: no .active(...) here — @Builder.Default sets it to true
                .build();
    }
}
