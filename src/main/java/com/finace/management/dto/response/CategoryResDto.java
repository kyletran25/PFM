package com.finace.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResDto {
    private Integer id;
    private String name;
    private String color;
    private Integer expense;
    private LocalDateTime createdDate;
}
