package com.ecommerce.main.category.controller;

import com.ecommerce.main.category.dto.AdminCategoryResponse;
import com.ecommerce.main.category.dto.CategoryResponse;
import com.ecommerce.main.category.dto.CreateCategoryRequest;
import com.ecommerce.main.category.dto.UpdateCategoryRequest;
import com.ecommerce.main.category.entity.Category;
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



    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse category = categoryService.createCategory(request);

        return ResponseEntity.ok().body(new ApiResponse("دسته جدید ایجاد شد", category));
    }

    @PutMapping("deactivate/{id}")
    public ResponseEntity<ApiResponse> deactivateCategory(@PathVariable("id") Integer id) {
        CategoryResponse categoryResponse = categoryService.deActivateCategory(id);

        return ResponseEntity.ok().body(new ApiResponse("دسته غیرفعال شد", categoryResponse));
    }

    @PutMapping("activate/{id}")
    public ResponseEntity<ApiResponse> activateCategory(@PathVariable("id") Integer id) {
        CategoryResponse categoryResponse = categoryService.activateCategory(id);
        return ResponseEntity.ok().body(new ApiResponse("دسته فعال شد", categoryResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<AdminCategoryResponse> adminCategoryResponses = categoryService.GetAllCategories();

        return ResponseEntity.ok().body(new ApiResponse("بازیابی شد", adminCategoryResponses));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.updateCategoryRequest(request);
        return ResponseEntity.ok().body(new ApiResponse("به روز رسانی شد", categoryResponse));
    }
}
