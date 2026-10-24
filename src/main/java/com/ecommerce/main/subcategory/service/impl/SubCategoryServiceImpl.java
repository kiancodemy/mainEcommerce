package com.ecommerce.main.subcategory.service.impl;

import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.category.repository.CategoryRepository;
import com.ecommerce.main.subcategory.dto.CreateSubCategory;
import com.ecommerce.main.subcategory.dto.UpdateSubCategory;
import com.ecommerce.main.subcategory.entity.SubCategory;
import com.ecommerce.main.subcategory.repository.SubCategoryRepository;
import com.ecommerce.main.subcategory.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SubCategory createsubCategory(CreateSubCategory request) {
        boolean exited = subCategoryRepository.existsByName(request.name());
        if (exited) {
            throw new UsernameNotFoundException("این دسته موجود می باشد نام دیگری انتخاب کنید");
        }

        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new UsernameNotFoundException("دسته انتخابی موجود نیست"));

        SubCategory newSubcategory = SubCategory.builder().name(request.name()).category(category).build();
        return subCategoryRepository.save(newSubcategory);
    }

    @Override
    public void deactivateSubCategory(Long  id) {
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("ریز دسته یافت نشد"));
        subCategory.setStatus(false);
    }

    @Override

    public void activateSubCategory(Long  id) {
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("ریز دسته یافت نشد"));
        subCategory.setStatus(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Override
    public void updateSubCategory(UpdateSubCategory request) {
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new UsernameNotFoundException("دسته انتخابی موجود نیست"));
        SubCategory subCategory = subCategoryRepository.findById(request.id()).orElseThrow(() -> new UsernameNotFoundException("ریز دسته یافت نشد"));
        subCategory.setCategory(category);
        subCategory.setName(request.name());
    }
}
