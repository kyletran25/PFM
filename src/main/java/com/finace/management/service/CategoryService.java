package com.finace.management.service;

import com.finace.management.dto.request.AddCategoryReqDto;
import com.finace.management.dto.response.CategoryResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.entity.Category;
import com.finace.management.exception.AppException;
import com.finace.management.exception.ErrorCode;
import com.finace.management.mapper.CategoryMapper;
import com.finace.management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper :: toCategoryResDto).toList();
    }

    public List<CategoryResDto> findAllByUser(AppUser appUser) {
        return categoryRepository.findByCreatedByAndIsDeletedIsFalse(appUser).stream().map(categoryMapper::toCategoryResDto).toList();
    }

    @Transactional
    public CategoryResDto addCategory(AppUser currentUser, AddCategoryReqDto addCategoryReqDto) {
        if (categoryRepository.existsByNameAndCreatedByAndIsDeletedIsFalse(addCategoryReqDto.getName(), currentUser)) {
            throw new AppException(ErrorCode.DUPLICATE_CATEGORY);
        }
        Category addCategory = Category.builder()
                .name(addCategoryReqDto.getName())
                .createdBy(currentUser)
                .isDeleted(false)
                .color(addCategoryReqDto.getColor() != null ? addCategoryReqDto.getColor() : "#bebebe")
                .build();
        return categoryMapper.toCategoryResDto(categoryRepository.save(addCategory));
    }

    @Transactional
    public CategoryResDto updateCategory(AppUser currentUser, Integer categoryId, AddCategoryReqDto addCategoryReqDto) {
        Category category = categoryRepository.findByIdAndCreatedByAndIsDeletedIsFalse(categoryId, currentUser)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        category.setName(addCategoryReqDto.getName());
        if (addCategoryReqDto.getColor() != null) {
            category.setColor(addCategoryReqDto.getColor());
        }
        return categoryMapper.toCategoryResDto(category);
    }

    @Transactional
    public CategoryResDto deleteCategory(AppUser currentUser, Integer categoryId) {
        Category category = categoryRepository.findByIdAndCreatedByAndIsDeletedIsFalse(categoryId, currentUser)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        category.setIsDeleted(true);
        return categoryMapper.toCategoryResDto(category);
    }
}
