package com.finace.management.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddIncomeMonthlyReqDto {
    @NotNull
    private String name;
    @NotNull
    private Integer startDay;
    private String description;
    @NotNull
    private Integer amount;
}
