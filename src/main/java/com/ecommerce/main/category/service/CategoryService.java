package com.ecommerce.main.category.service;

import com.ecommerce.main.category.dto.CreateCategoryRequest;
import com.ecommerce.main.category.dto.UpdateCategoryRequest;
import com.ecommerce.main.category.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CreateCategoryRequest name);

    void deActivateCategory(Integer id);

    void activateCategory(Integer id);

    List<Category> GetAllCategories();

    void updateCategoryRequest(UpdateCategoryRequest request);


}
