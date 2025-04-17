package com.finace.management.service;

import com.finace.management.dto.request.AddCategoryReqDto;
import com.finace.management.dto.request.UpdateCategoryReqDto;
import com.finace.management.dto.response.CategoryResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.entity.Category;
import com.finace.management.entity.IncomeMonthly;
import com.finace.management.exception.AppException;
import com.finace.management.exception.ErrorCode;
import com.finace.management.mapper.CategoryMapper;
import com.finace.management.repository.CategoryRepository;
import com.finace.management.repository.IncomeMonthlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final IncomeMonthlyRepository incomeMonthlyRepository;

    public List<CategoryResDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper :: toCategoryResDto).toList();
    }

    public List<CategoryResDto> findAllByUser(AppUser appUser) {
        return categoryRepository.findByCreatedByAndIsDeletedIsFalse(appUser).stream().map(categoryMapper::toCategoryResDto).toList();
    }

    @Transactional
    public CategoryResDto addCategory(AppUser currentUser, AddCategoryReqDto addCategoryReqDto) {
        // get income monthly
        IncomeMonthly incomeMonthly = incomeMonthlyRepository.findFirstByUser(currentUser)
                .orElseThrow(() -> new AppException(ErrorCode.INCOME_MONTH_NOT_EXISTED));
        if (categoryRepository.existsByNameAndCreatedByAndIsDeletedIsFalse(addCategoryReqDto.getName(), currentUser)) {
            throw new AppException(ErrorCode.DUPLICATE_CATEGORY);
        }
        // Check expense exceeds the income
        Integer totalExpense = categoryRepository
                .findByCreatedByAndIsDeletedIsFalse(currentUser).stream()
                .map(Category::getExpense)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);

        int newTotalExpense = totalExpense + addCategoryReqDto.getExpense();

        if (newTotalExpense > incomeMonthly.getAmount()) {
            throw new AppException(ErrorCode.CATEGORY_EXPENSE_EXCEEDS_INCOME);
        }

        Category addCategory = Category.builder()
                .name(addCategoryReqDto.getName())
                .createdBy(currentUser)
                .isDeleted(false)
                .color(addCategoryReqDto.getColor() != null ? addCategoryReqDto.getColor() : "#bebebe")
                .expense(addCategoryReqDto.getExpense())
                .build();
        return categoryMapper.toCategoryResDto(categoryRepository.save(addCategory));
    }

    @Transactional
    public CategoryResDto updateCategory(AppUser currentUser, Integer categoryId, UpdateCategoryReqDto updateCategoryReqDto) {
        Category category = categoryRepository.findByIdAndCreatedByAndIsDeletedIsFalse(categoryId, currentUser)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        if(updateCategoryReqDto.getName() != null){
            category.setName(updateCategoryReqDto.getName());
        }
        if(updateCategoryReqDto.getExpense() != null){
            Integer newExpense = updateCategoryReqDto.getExpense();

            // Check expense exceeds the income
            IncomeMonthly incomeMonthly = incomeMonthlyRepository.findFirstByUser(currentUser)
                    .orElseThrow(() -> new AppException(ErrorCode.INCOME_MONTH_NOT_EXISTED));
            Integer totalOtherExpense = categoryRepository
                    .findByCreatedByAndIsDeletedIsFalse(currentUser).stream()
                    .filter(c -> !c.getId().equals(categoryId))
                    .map(Category::getExpense)
                    .filter(Objects::nonNull)
                    .reduce(0, Integer::sum);

            int totalAfterUpdate = totalOtherExpense + newExpense;

            if (totalAfterUpdate > incomeMonthly.getAmount()) {
                throw new AppException(ErrorCode.CATEGORY_EXPENSE_EXCEEDS_INCOME);
            }

            category.setExpense(updateCategoryReqDto.getExpense());
        }
        if (updateCategoryReqDto.getColor() != null) {
            category.setColor(updateCategoryReqDto.getColor());
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
