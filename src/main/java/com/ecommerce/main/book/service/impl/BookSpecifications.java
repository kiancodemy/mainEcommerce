package com.ecommerce.main.book.service.impl;

import com.ecommerce.main.book.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;


public final class BookSpecifications {

    private BookSpecifications() {}

    public static Specification<Book> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    public static Specification<Book> hasCategory(Long categoryId) {
        if (categoryId == null) return null;                     // null = no filter
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }
    public static Specification<Book> hasSubCategory(Long subcategoryId) {
        if (subcategoryId == null) return null;                     // null = no filter
        return (root, query, cb) -> cb.equal(root.get("subCategory").get("id"),subcategoryId);
    }


    /*public static Specification<Book> hasAuthor(Long authorId) {
        if (authorId == null) return null;
        return (root, query, cb) -> {
            Join<Book, Author> authors = root.join("authors");
            query.distinct(true);                                // a join can repeat rows
            return cb.equal(authors.get("id"), authorId);
        };
    }*/

    public static Specification<Book> priceAtLeast(BigDecimal min) {
        if (min == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Book> priceAtMost(BigDecimal max) {
        if (max == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), max);
    }

    public static Specification<Book> titleContains(String text) {
        if (text == null || text.isBlank()) return null;
        String escaped = text.trim().toLowerCase()
                .replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + escaped + "%", '\\');
    }

    public static Specification<Book> inStock(Boolean inStock) {
        if (inStock == null || !inStock) return null;
        return (root, query, cb) -> cb.greaterThan(root.get("stock"), 0);
    }

    public static Specification<Book> hasLanguage(String language) {
        if (language == null || language.isBlank()) return null;
        return (root, query, cb) -> cb.equal(root.get("language"), language);
    }


}