package com.ecommerce.main.category.dto;

import com.ecommerce.main.subcategory.dto.SubCategoryResponse;
import com.ecommerce.main.subcategory.entity.SubCategory;

import java.util.Set;

public record AdminCategoryResponse(
        Integer id,
        String name,

        boolean status,

        Set<SubCategoryResponse> subCategories

) {
}
