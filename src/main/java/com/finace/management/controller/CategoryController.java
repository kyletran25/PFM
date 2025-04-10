package com.finace.management.controller;

import com.finace.management.dto.request.AddCategoryReqDto;
import com.finace.management.dto.request.UpdateCategoryReqDto;
import com.finace.management.dto.response.ApiResponse;
import com.finace.management.dto.response.CategoryResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
@PreAuthorize("hasAuthority('STATUS_ACTIVE')")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    @Operation(summary = "Get category list belong to current user")
    public ApiResponse<List<CategoryResDto>> getAllCategoryByUid(@AuthenticationPrincipal AppUser currentUser) {
        List<CategoryResDto> categoryResDtoList = categoryService.findAllByUser(currentUser);
        return ApiResponse.<List<CategoryResDto>>builder().result(categoryResDtoList).build();
    }

    @PostMapping()
    @Operation(summary = "Add new category")
    public ApiResponse<CategoryResDto> addNewCategory(@AuthenticationPrincipal AppUser currentUser,
                                                      @RequestBody @Valid AddCategoryReqDto addCategoryReqDto) {

        return ApiResponse.<CategoryResDto>builder()
                .result(categoryService.addCategory(currentUser, addCategoryReqDto))
                .message("Add category successfully").build();
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Update category")
    public ApiResponse<CategoryResDto> updateCategory(@AuthenticationPrincipal AppUser currentUser,
                                                      @PathVariable(name = "categoryId") Integer categoryId,
                                                      @RequestBody @Valid UpdateCategoryReqDto addCategoryReqDto) {
        return ApiResponse.<CategoryResDto>builder()
                .result(categoryService.updateCategory(currentUser, categoryId, addCategoryReqDto))
                .message("Update category successfully").build();
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete category")
    public ApiResponse<CategoryResDto> deleteCategory(@AuthenticationPrincipal AppUser currentUser,
                                                      @PathVariable(name = "categoryId") Integer categoryId) {
        return ApiResponse.<CategoryResDto>builder()
                .result(categoryService.deleteCategory(currentUser, categoryId))
                .message("Delete category successfully")
                .build();
    }
}
