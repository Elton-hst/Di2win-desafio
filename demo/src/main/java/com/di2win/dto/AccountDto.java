package com.di2win.dto;

import com.di2win.entity.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AccountDto(String name,
                         String document,
                         BigDecimal balance,
                         LocalDateTime birth,
                         Long agency,
                         Long account,
                         AccountType accountType) {


}
