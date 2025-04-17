package com.finace.management.repository;

import com.finace.management.entity.AppUser;
import com.finace.management.entity.IncomeMonthly;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface IncomeMonthlyRepository extends JpaRepository<IncomeMonthly, Integer> {
    Optional<IncomeMonthly> findFirstByUser(AppUser user);
}
