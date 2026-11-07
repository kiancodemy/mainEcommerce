package com.ecommerce.main.category.controller;

import com.ecommerce.main.category.dto.AdminCategoryResponse;
import com.ecommerce.main.category.dto.CategoryResponse;
import com.ecommerce.main.category.dto.CreateCategoryRequest;
import com.ecommerce.main.category.dto.UpdateCategoryRequest;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.category.service.impl.CategoryServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/categories")
@RequiredArgsConstructor
@Tag(name="admin category controller")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class AdminCategoryController {
    private final CategoryServiceImpl categoryService;



    @PostMapping
    @Operation(summary = "create new category")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse category = categoryService.createCategory(request);

        return ResponseEntity.ok().body(new ApiResponse("دسته جدید ایجاد شد", category));
    }

    @PutMapping("/deactivate/{id}")
    @Operation(summary = "deactivate category")
    public ResponseEntity<ApiResponse> deactivateCategory(@PathVariable("id") Long  id) {
        CategoryResponse categoryResponse = categoryService.deActivateCategory(id);

        return ResponseEntity.ok().body(new ApiResponse("دسته غیرفعال شد", categoryResponse));
    }

    @PutMapping("/activate/{id}")
    @Operation(summary = "activate category")
    public ResponseEntity<ApiResponse> activateCategory(@PathVariable("id") Long  id) {
        CategoryResponse categoryResponse = categoryService.activateCategory(id);
        return ResponseEntity.ok().body(new ApiResponse("دسته فعال شد", categoryResponse));
    }

    @GetMapping
    @Operation(summary = "get All category")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<AdminCategoryResponse> adminCategoryResponses = categoryService.GetAllCategories();

        return ResponseEntity.ok().body(new ApiResponse("بازیابی شد", adminCategoryResponses));
    }

    @PutMapping
    @Operation(summary = "update category")
    public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.updateCategoryRequest(request);
        return ResponseEntity.ok().body(new ApiResponse("به روز رسانی شد", categoryResponse));
    }
}
