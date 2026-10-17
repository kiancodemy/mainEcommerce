package com.ecommerce.main.subcategory.repository;

import com.ecommerce.main.subcategory.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer> {
    boolean existsByName(String name);

}
