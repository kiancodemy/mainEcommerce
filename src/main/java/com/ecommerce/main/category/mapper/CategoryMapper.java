package com.ecommerce.main.category.mapper;

import com.ecommerce.main.category.dto.AdminCategoryResponse;
import com.ecommerce.main.category.dto.CategoryResponse;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.subcategory.dto.SubCategoryResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.isStatus());
    }

    public List<AdminCategoryResponse> toAllCategoryResponse(List<Category> categories) {
        return
                categories.stream().map(c -> new AdminCategoryResponse(
                        c.getId(),
                        c.getName(),
                        c.isStatus(),
                        c.getSubCategories().stream()
                                .map(s -> new SubCategoryResponse(s.getId(), s.getName(),s.isStatus())).collect(Collectors.toSet()))).toList();



    }
}
