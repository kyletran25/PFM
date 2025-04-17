package com.finace.management.service;

import com.finace.management.dto.request.AddItemReqDto;
import com.finace.management.dto.request.UpdateItemReqDto;
import com.finace.management.dto.response.ItemResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.entity.Category;
import com.finace.management.entity.CategoryPeriod;
import com.finace.management.entity.Item;
import com.finace.management.exception.AppException;
import com.finace.management.exception.ErrorCode;
import com.finace.management.mapper.ItemMapper;
import com.finace.management.repository.CategoryPeriodRepository;
import com.finace.management.repository.CategoryRepository;
import com.finace.management.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final CategoryPeriodRepository categoryPeriodRepository;
    private final CategoryService categoryService;
    private final CategoryPeriodService categoryPeriodService;

    @Transactional
    public ItemResDto addItem(AppUser currentUser, AddItemReqDto item) {
        // Get category
        Category category = categoryRepository
                .findByIdAndCreatedByAndIsDeletedIsFalse(item.getCategoryId(), currentUser)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        // Get category period
        // If not exist add new category period
        LocalDateTime createdDate = item.getCreatedDate();
        CategoryPeriod categoryPeriod = categoryPeriodRepository
                .findByCategoryAndStartDateBeforeAndEndDateAfter(category, createdDate, createdDate).orElseGet(
                        () -> categoryPeriodService.add(category, createdDate.getMonthValue(), createdDate.getYear())
                );

        Item addItem = Item.builder()
                .name(item.getName())
                .categoryPeriod(categoryPeriod)
                .createdDate(item.getCreatedDate())
                .createdBy(currentUser)
                .description(item.getDescription())
                .value(item.getValue())
                .build();
        return itemMapper.toItemResDto(itemRepository.save(addItem));
    }

    @Transactional
    public ItemResDto updateItem(AppUser currentUser, UpdateItemReqDto item, Integer itemId) {
        Item updateItem = itemRepository.findByIdAndCreatedBy(itemId, currentUser)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_EXISTED));
        if(item.getName() != null){
            updateItem.setName(item.getName());
        }
        if(item.getCategoryId() != null){
            Category category = categoryRepository
                    .findByIdAndCreatedByAndIsDeletedIsFalse(item.getCategoryId(), currentUser)
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

            // Get category period
            // If not exist add new category period
            LocalDateTime createdDate = item.getCreatedDate();
            CategoryPeriod categoryPeriod = categoryPeriodRepository
                    .findByCategoryAndStartDateBeforeAndEndDateAfter(category, createdDate, createdDate).orElseGet(
                            () -> categoryPeriodService.add(category, createdDate.getMonthValue(), createdDate.getYear())
                    );

            updateItem.setCategoryPeriod(categoryPeriod);
        }
        if(item.getDescription() != null){
            updateItem.setDescription(item.getDescription());
        }
        if(item.getValue() != null){
            updateItem.setValue(item.getValue());
        }
        if(item.getCreatedDate() != null){
            updateItem.setCreatedDate(item.getCreatedDate());
        }
        return itemMapper.toItemResDto(updateItem);
    }

    @Transactional
    public ItemResDto deleteItem(AppUser currentUser, Integer itemId) {
        Item item = itemRepository.findByIdAndCreatedBy(itemId, currentUser)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_EXISTED));
        ItemResDto resDto = itemMapper.toItemResDto(item);
        itemRepository.delete(item);
        return resDto;
    }
}
