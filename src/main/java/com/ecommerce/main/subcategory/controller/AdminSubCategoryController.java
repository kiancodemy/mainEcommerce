package com.ecommerce.main.subcategory.controller;

import com.ecommerce.main.common.components.FileStorageService;
import com.ecommerce.main.common.dto.ApiResponse;
import com.ecommerce.main.subcategory.dto.CreateSubCategory;
import com.ecommerce.main.subcategory.dto.SubCategoryResponse;
import com.ecommerce.main.subcategory.dto.UpdateSubCategory;
import com.ecommerce.main.subcategory.entity.SubCategory;
import com.ecommerce.main.subcategory.mapper.SubCategoryMapper;
import com.ecommerce.main.subcategory.service.impl.SubCategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/subcategory/")
@RequiredArgsConstructor
public class AdminSubCategoryController {
    private final SubCategoryServiceImpl subCategoryService;
    private final SubCategoryMapper subCategoryMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> createSubCategory(@Valid @RequestBody CreateSubCategory request) {
        subCategoryService.createsubCategory(request);
        return ResponseEntity.ok().body(new ApiResponse(" زیر دسته جدید ایجاد شد", null));
    }

    @PutMapping("deactivate/{id}")
    public ResponseEntity<ApiResponse> deactivateSubCategory(@PathVariable("id") Long  id) {
        subCategoryService.deactivateSubCategory(id);

        return ResponseEntity.ok().body(new ApiResponse("زیر دسته غیرفعال شد", null));
    }

    @PutMapping("activate/{id}")
    public ResponseEntity<ApiResponse> activateSubCategory(@PathVariable("id") Long id) {
        subCategoryService.activateSubCategory(id);
        return ResponseEntity.ok().body(new ApiResponse("زیر دسته فعال شد", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> activateSubCategory() {
        List<SubCategory> allSubCategory = subCategoryService.getAllSubCategories();
        List<SubCategoryResponse> allCategoryResponse = subCategoryMapper.toAllSubCategoryResponse(allSubCategory);
        return ResponseEntity.ok().body(new ApiResponse("بازیابی شد", allCategoryResponse));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateSubCategory(@Valid @RequestBody UpdateSubCategory request) {
        subCategoryService.updateSubCategory(request);
        return ResponseEntity.ok().body(new ApiResponse("به روز رسانی شد", null));
    }




}
