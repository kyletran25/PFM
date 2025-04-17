package com.finace.management.service;

import com.finace.management.dto.request.AddIncomeMonthlyReqDto;
import com.finace.management.dto.response.IncomeMonthlyResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.entity.IncomeMonthly;
import com.finace.management.exception.AppException;
import com.finace.management.exception.ErrorCode;
import com.finace.management.mapper.IncomeMonthlyMapper;
import com.finace.management.repository.IncomeMonthlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IncomeMonthlyService {
    private final IncomeMonthlyRepository incomeMonthlyRepository;
    private final IncomeMonthlyMapper incomeMonthlyMapper;

    @Transactional
    public IncomeMonthlyResDto add(AppUser currentUser, AddIncomeMonthlyReqDto reqDto) {
        // check just allow to add only 1 icml
        incomeMonthlyRepository.findFirstByUser(currentUser).ifPresent(incomeMonthly -> {
            throw new AppException(ErrorCode.INCOME_MONTHLY_EXISTED);
        });
        IncomeMonthly addIncomeMonthly = IncomeMonthly.builder()
                .user(currentUser)
                .name(reqDto.getName())
                .startDay(reqDto.getStartDay())
                .description(reqDto.getDescription())
                .amount(reqDto.getAmount())
                .build();
        return incomeMonthlyMapper.toIncomeMonthlyResDto(incomeMonthlyRepository.save(addIncomeMonthly));
    }
}
