package com.ecommerce.main.subcategory.controller;

import com.ecommerce.main.common.dto.ApiResponse;
import com.ecommerce.main.subcategory.dto.CreateSubCategory;
import com.ecommerce.main.subcategory.dto.SubCategoryResponse;
import com.ecommerce.main.subcategory.dto.UpdateSubCategory;
import com.ecommerce.main.subcategory.service.impl.SubCategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/subcategory")
@RequiredArgsConstructor
@Tag(name="admin SubCategory controller")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSubCategoryController {
    private final SubCategoryServiceImpl subCategoryService;

    @PostMapping
    @Operation(summary = "create subCategory")
    public ResponseEntity<ApiResponse> createSubCategory(@Valid @RequestBody CreateSubCategory request) {
        subCategoryService.createsubCategory(request);
        return ResponseEntity.ok().body(new ApiResponse(" زیر دسته جدید ایجاد شد", null));
    }

    @Operation(summary = "deActivate subCategory")
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponse> deactivateSubCategory(@PathVariable("id") Long  id) {
        subCategoryService.deactivateSubCategory(id);

        return ResponseEntity.ok().body(new ApiResponse("زیر دسته غیرفعال شد", null));
    }

    @Operation(summary = "activate subCategory")
    @PutMapping("/activate/{id}")
    public ResponseEntity<ApiResponse> activateSubCategory(@PathVariable("id") Long id) {
        subCategoryService.activateSubCategory(id);
        return ResponseEntity.ok().body(new ApiResponse("زیر دسته فعال شد", null));
    }

    @Operation(summary = "get All subCategory")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllSubCategories() {
        List<SubCategoryResponse> allSubCategories = subCategoryService.getAllSubCategories();

        return ResponseEntity.ok().body(new ApiResponse("بازیابی شد", allSubCategories));
    }

    @Operation(summary = "update subCategory")
    @PutMapping
    public ResponseEntity<ApiResponse> updateSubCategory(@Valid @RequestBody UpdateSubCategory request) {
        subCategoryService.updateSubCategory(request);
        return ResponseEntity.ok().body(new ApiResponse("به روز رسانی شد", null));
    }




}
