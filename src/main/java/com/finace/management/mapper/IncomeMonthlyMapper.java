package com.finace.management.mapper;

import com.finace.management.dto.response.IncomeMonthlyResDto;
import com.finace.management.entity.IncomeMonthly;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncomeMonthlyMapper {
    IncomeMonthlyResDto toIncomeMonthlyResDto(IncomeMonthly incomeMonthly);
}
