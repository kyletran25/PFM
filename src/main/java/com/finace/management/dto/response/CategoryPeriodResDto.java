package com.finace.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryPeriodResDto {
    private Integer id;
    private CategoryResDto category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer amount;
    private LocalDateTime createdDate;
    private String yearMonth;
}
