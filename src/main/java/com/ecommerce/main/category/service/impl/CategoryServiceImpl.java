package com.ecommerce.main.category.service.impl;

import com.ecommerce.main.category.dto.CreateCategoryRequest;
import com.ecommerce.main.category.dto.UpdateCategoryRequest;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.category.repository.CategoryRepository;
import com.ecommerce.main.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createCategory(CreateCategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new UsernameNotFoundException("نام دسته بندی تکراری است");
        }
        Category category = Category.builder()
                .name(request.name())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deActivateCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("دسته بندی یافت نشد"));

        category.setStatus(false);
    }

    @Override
    @Transactional
    public void activateCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("دسته بندی یافت نشد"));
        category.setStatus(true);
    }

    @Override
    public List<Category> GetAllCategories() {
        return categoryRepository.getCategoriesWithSubcategory();
    }

    @Override
    @Transactional
    public void updateCategoryRequest(UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(request.id()).orElseThrow(() -> new UsernameNotFoundException("ذسته بندی یافت نشد"));
        category.setName(request.name());
    }

}