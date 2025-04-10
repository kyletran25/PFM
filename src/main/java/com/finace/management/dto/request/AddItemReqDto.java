package com.finace.management.dto.request;

import com.finace.management.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemReqDto {
    @NotBlank
    private String name;
    @NotNull
    private Integer value;
    @NotNull
    private Integer categoryId;
    @NotNull
    private LocalDateTime createdDate;
    private String description;
}
