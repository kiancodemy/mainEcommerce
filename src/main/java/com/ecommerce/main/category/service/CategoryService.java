package com.ecommerce.main.category.service;

import com.ecommerce.main.category.dto.AdminCategoryResponse;
import com.ecommerce.main.category.dto.CategoryResponse;
import com.ecommerce.main.category.dto.CreateCategoryRequest;
import com.ecommerce.main.category.dto.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest name);

    CategoryResponse deActivateCategory(Integer id);

    CategoryResponse activateCategory(Integer id);

    List<AdminCategoryResponse> GetAllCategories();

    CategoryResponse updateCategoryRequest(UpdateCategoryRequest request);


}
