package com.ecommerce.main.category.service.impl;

import com.ecommerce.main.category.dto.AdminCategoryResponse;
import com.ecommerce.main.category.dto.CategoryResponse;
import com.ecommerce.main.category.dto.CreateCategoryRequest;
import com.ecommerce.main.category.dto.UpdateCategoryRequest;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.category.mapper.CategoryMapper;
import com.ecommerce.main.category.repository.CategoryRepository;
import com.ecommerce.main.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new UsernameNotFoundException("نام دسته بندی تکراری است");
        }
        Category category = Category.builder()
                .name(request.name())
                .build();
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse deActivateCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("دسته بندی یافت نشد"));

        category.setStatus(false);
        return categoryMapper.toResponse(category);

    }

    @Override
    public CategoryResponse activateCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("دسته بندی یافت نشد"));
        category.setStatus(true);
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminCategoryResponse> GetAllCategories() {
        return categoryMapper.toResponse(categoryRepository.findAllBy());
    }

    @Override

    public CategoryResponse updateCategoryRequest(UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(request.id()).orElseThrow(() -> new UsernameNotFoundException("ذسته بندی یافت نشد"));
        category.setName(request.name());
        return categoryMapper.toResponse(category);
    }

}