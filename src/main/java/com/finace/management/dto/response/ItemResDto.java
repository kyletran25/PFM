package com.finace.management.dto.response;

import com.finace.management.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResDto {
    private Integer id;
    private String name;
    private Integer value;
    private CategoryResDto category;
    private LocalDateTime createdDate;
    private String description;
}
