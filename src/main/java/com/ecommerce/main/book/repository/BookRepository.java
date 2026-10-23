package com.ecommerce.main.book.repository;

import com.ecommerce.main.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIdAndStatusTrue(Integer id);
}