package com.ecommerce.main.book.repository;

import com.ecommerce.main.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select s from Book s where s.id=?1 and s.status=true")
    Optional<Book> findByIdAndStatusTrue(Integer id);
}