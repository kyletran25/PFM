package com.finace.management.mapper;

import com.finace.management.dto.response.CategoryPeriodResDto;
import com.finace.management.entity.CategoryPeriod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryPeriodMapper {
    CategoryPeriodResDto toCategoryPeriodResDto(CategoryPeriod categoryPeriod);
}
