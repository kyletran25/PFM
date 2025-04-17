package com.finace.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "income_monthly_period")
public class IncomeMonthlyPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "income_monthly_id", nullable = false)
    private IncomeMonthly incomeMonthly;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Size(max = 25)
    @NotNull
    @Column(name = "`year_month`", nullable = false, length = 25)
    private String yearMonth;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @PrePersist
    protected void onPersist() {
        this.createdDate = LocalDateTime.now();
    }
}