package com.ecommerce.main.subcategory.service;

import com.ecommerce.main.subcategory.dto.CreateSubCategory;
import com.ecommerce.main.subcategory.dto.UpdateSubCategory;
import com.ecommerce.main.subcategory.entity.SubCategory;

import java.util.List;

public interface SubCategoryService {
    SubCategory createsubCategory(CreateSubCategory request);

    void deactivateSubCategory(Long  id);

    void activateSubCategory(Long  id);

    List<SubCategory> getAllSubCategories();

    void updateSubCategory(UpdateSubCategory request);

}
