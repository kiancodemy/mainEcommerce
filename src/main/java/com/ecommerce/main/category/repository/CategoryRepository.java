package com.ecommerce.main.category.repository;

import com.ecommerce.main.category.entity.Category;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameIgnoreCase(String name);

    @Query("select distinct c from Category c left join fetch c.subCategories")
    List<Category> getCategoriesWithSubcategory();

}
