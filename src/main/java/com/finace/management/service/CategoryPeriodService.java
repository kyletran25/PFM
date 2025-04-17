package com.finace.management.service;

import com.finace.management.entity.Category;
import com.finace.management.entity.CategoryPeriod;
import com.finace.management.entity.IncomeMonthly;
import com.finace.management.exception.AppException;
import com.finace.management.exception.ErrorCode;
import com.finace.management.repository.CategoryPeriodRepository;
import com.finace.management.repository.IncomeMonthlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class CategoryPeriodService {
    private final IncomeMonthlyRepository incomeMonthlyRepository;
    private final CategoryPeriodRepository categoryPeriodRepository;

    // add new category period of current month
    @Transactional
    public CategoryPeriod add(Category category, Integer month, Integer year) {
        // Get Income Monthly
        IncomeMonthly incomeMonthly = incomeMonthlyRepository.findFirstByUser(category.getCreatedBy())
                .orElseThrow(() -> new AppException(ErrorCode.INCOME_MONTH_NOT_EXISTED));

        int startDay = incomeMonthly.getStartDay();

        YearMonth ym = YearMonth.of(year, month);

        LocalDate startDate = ym.atDay(startDay);
        LocalDate endDate = startDate.plusMonths(1).withDayOfMonth(startDay - 1);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59, 999_999_000); // max cho datetime(6)

        String yearMonth = ym.toString(); // "2025-04"

        CategoryPeriod categoryPeriod = CategoryPeriod.builder()
                .category(category)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .yearMonth(yearMonth)
                .amount(category.getExpense())
                .build();
        return categoryPeriodRepository.save(categoryPeriod);
    }
}
