package com.di2win.web.dto;

import com.di2win.domin.entity.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;


public record AccountDto(String name,
                         String document,
                         BigDecimal balance,
                         LocalDate birth,
                         Long agency,
                         Long account,
                         AccountType accountType) {

}
