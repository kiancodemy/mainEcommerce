package com.ecommerce.main.category.controller;

import com.ecommerce.main.category.dto.AdminCategoryResponse;
import com.ecommerce.main.category.dto.CategoryResponse;
import com.ecommerce.main.category.dto.CreateCategoryRequest;
import com.ecommerce.main.category.dto.UpdateCategoryRequest;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.category.mapper.CategoryMapper;
import com.ecommerce.main.category.service.impl.CategoryServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categories/")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")//
public class AdminCategoryController {
    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;


    @PostMapping("createCategory")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        Category category = categoryService.createCategory(request);
        CategoryResponse response = categoryMapper.toResponse(category);
        return ResponseEntity.ok().body(new ApiResponse("دسته جدید ایجاد شد", response));
    }

    @PutMapping("deactivate/{id}")
    public ResponseEntity<ApiResponse> deactivateCategory(@PathVariable("id") Integer id) {
        categoryService.deActivateCategory(id);

        return ResponseEntity.ok().body(new ApiResponse("دسته غیرفعال شد", null));
    }

    @PutMapping("activate/{id}")
    public ResponseEntity<ApiResponse> activateCategory(@PathVariable("id") Integer id) {
        categoryService.activateCategory(id);
        return ResponseEntity.ok().body(new ApiResponse("دسته فعال شد", null));
    }

    @GetMapping("allCategory")
    public ResponseEntity<ApiResponse> activateCategory() {
        List<Category> categories = categoryService.GetAllCategories();
        List<AdminCategoryResponse> adminCategoryResponses = categoryMapper.toAllCategoryResponse(categories);
        return ResponseEntity.ok().body(new ApiResponse("بازیابی شد", adminCategoryResponses));
    }

    @PutMapping("updatecategory")
    public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest request) {
        categoryService.updateCategoryRequest(request);
        return ResponseEntity.ok().body(new ApiResponse("به روز رسانی شد", null));
    }
}
