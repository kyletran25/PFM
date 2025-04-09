package com.finace.management.mapper;

import com.finace.management.dto.response.CategoryResDto;
import com.finace.management.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResDto toCategoryResDto(Category category);
}
