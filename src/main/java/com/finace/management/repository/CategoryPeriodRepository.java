package com.finace.management.repository;

import com.finace.management.entity.Category;
import com.finace.management.entity.CategoryPeriod;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface CategoryPeriodRepository extends JpaRepository<CategoryPeriod, Integer> {
    Optional<CategoryPeriod> findByCategoryAndStartDateBeforeAndEndDateAfter(@NotNull Category category, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate);
}
