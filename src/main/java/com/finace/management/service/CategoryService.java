package com.finace.management.service;

import com.finace.management.dto.response.CategoryResDto;
import com.finace.management.mapper.CategoryMapper;
import com.finace.management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper :: toCategoryResDto).toList();
    }
}
