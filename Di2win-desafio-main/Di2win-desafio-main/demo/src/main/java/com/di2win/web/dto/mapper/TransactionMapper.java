package com.di2win.web.dto.mapper;

import com.di2win.domin.entity.Account;
import com.di2win.domin.service.AccountService;
import com.di2win.web.dto.TransactionDto;
import com.di2win.domin.entity.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class TransactionMapper {
    public static Transaction toEntity(TransactionDto dto, Account account) throws Exception {
        if(dto == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.value());
        transaction.setTransactionType(dto.transactionType());
        transaction.setReceiver(account);
        transaction.setTimestamp(LocalDateTime.now());
        return transaction;
    }


}
