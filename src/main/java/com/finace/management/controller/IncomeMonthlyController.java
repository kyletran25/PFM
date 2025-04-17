package com.finace.management.controller;

import com.finace.management.dto.request.AddIncomeMonthlyReqDto;
import com.finace.management.dto.response.ApiResponse;
import com.finace.management.dto.response.IncomeMonthlyResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.service.IncomeMonthlyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/income_monthly")
@PreAuthorize("hasAuthority('STATUS_ACTIVE')")
public class IncomeMonthlyController {
    private final IncomeMonthlyService incomeMonthlyService;

    @PostMapping
    @Operation(summary = "Add income monthly (Currently just allow to add only 1 income monthly")
    public ApiResponse<IncomeMonthlyResDto> addNewIncomeMonthly(@AuthenticationPrincipal AppUser currentUser,
                                                                @RequestBody @Valid AddIncomeMonthlyReqDto reqDto) {

        return ApiResponse.<IncomeMonthlyResDto>builder()
                .result(incomeMonthlyService.add(currentUser, reqDto))
                .message("Add monthly income successfully").build();
    }
}
