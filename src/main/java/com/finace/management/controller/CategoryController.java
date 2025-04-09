package com.finace.management.controller;

import com.finace.management.dto.response.ApiResponse;
import com.finace.management.dto.response.CategoryResDto;
import com.finace.management.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ApiResponse<List<CategoryResDto>> getAll() {
        List<CategoryResDto> categoryResDtoList = categoryService.findAll();
        return ApiResponse.<List<CategoryResDto>>builder().result(categoryResDtoList).build();
    }
}
