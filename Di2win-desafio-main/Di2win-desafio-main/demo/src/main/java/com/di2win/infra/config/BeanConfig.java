package com.di2win.infra.config;

import com.di2win.domin.service.AccountService;
import com.di2win.domin.service.TransactionService;
import com.di2win.infra.repository.AccountRepository;
import com.di2win.infra.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountService(accountRepository);
    }
    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository, AccountService accountService) {
        return new TransactionService(accountService,transactionRepository);
    }

}
