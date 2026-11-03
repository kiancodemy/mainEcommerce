package com.ecommerce.main.book.repository;

import com.ecommerce.main.book.entity.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long > {

    @Query("SELECT s from Book s WHERE s.id=?1 AND s.status=true")
    Optional<Book> findByIdAndStatusTrue(Integer id);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Book b WHERE b.id=?1")
    Book findByIdLocked(Long id);
}