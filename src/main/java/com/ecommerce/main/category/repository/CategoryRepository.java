package com.ecommerce.main.category.repository;

import com.ecommerce.main.category.entity.Category;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long > {
    boolean existsByNameIgnoreCase(String name);

    @EntityGraph(attributePaths = "subCategories")
    List<Category> findAllBy();

}
