package com.ecommerce.main.subcategory.mapper;

import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.subcategory.dto.SubCategoryResponse;
import com.ecommerce.main.subcategory.entity.SubCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubCategoryMapper {

    public List<SubCategoryResponse> toAllSubCategoryResponse(List<SubCategory> subCategories) {
        return subCategories.stream().map(c -> new SubCategoryResponse(c.getId(), c.getName(), c.isStatus())).toList();
    }
}
