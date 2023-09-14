package com.di2win.dto;

import com.di2win.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDto(BigDecimal amount,
                             BigDecimal value,
                             long senderId,
                             long reciverId,
                             LocalDate timeTemp,
                             TransactionType transactionType) {

}

