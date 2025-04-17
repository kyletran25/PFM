package com.finace.management.dto.response;

import com.finace.management.entity.AppUser;
import jakarta.persistence.*;
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
public class IncomeMonthlyResDto {
    private Integer id;
    private String name;
    private Integer startDay;
    private String description;
    private LocalDateTime createdDate;
    private Integer amount;
}
