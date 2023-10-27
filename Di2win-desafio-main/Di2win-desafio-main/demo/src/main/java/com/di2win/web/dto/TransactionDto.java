package com.di2win.web.dto;

import com.di2win.domin.entity.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(BigDecimal amount,
                             BigDecimal value,
                             long agency,
                             long account,
                             LocalDateTime timeTemp,
                             TransactionType transactionType) {


}

