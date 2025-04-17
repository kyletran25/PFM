package com.finace.management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
