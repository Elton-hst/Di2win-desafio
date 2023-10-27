package com.di2win.demo.transaction;

import com.di2win.web.dto.TransactionDto;
import com.di2win.domin.entity.Account;
import com.di2win.domin.entity.enums.AccountType;
import com.di2win.domin.entity.enums.TransactionType;
import com.di2win.infra.repository.TransactionRepository;
import com.di2win.domin.service.AccountService;
import com.di2win.domin.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @Mock
    private AccountService accountService;
    @Mock
    private TransactionRepository repository;
    @Autowired
    @InjectMocks
    private TransactionService transactionService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception {
        Account sender = new Account(
                1L,
                "eltin",
                new BigDecimal(100.0),
                LocalDate.parse("2000-09-09" ,DateTimeFormatter.ISO_LOCAL_DATE),
                "11450434452",
                123L,
                321L,
                AccountType.active);

        Account receiver = new Account(
                2L,
                "Maria",
                new BigDecimal(100.0),
                LocalDate.parse("2000-09-09" ,DateTimeFormatter.ISO_LOCAL_DATE),
                "65602501487",
                321L,
                321L,
                AccountType.active);

        when(accountService.findAccountsById(1L)).thenReturn(sender);
        when(accountService.findAccountsById(2L)).thenReturn(receiver);

        TransactionDto request = new TransactionDto(
                new BigDecimal(100),
                new BigDecimal(100),
                2L,
                1L,
                LocalDateTime.now(),
                TransactionType.transactionForUser);
        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(accountService, times(1)).saveAccount(sender);

        receiver.setBalance(new BigDecimal(100));
        verify(accountService, times(1)).saveAccount(receiver);
    }

    @Test
    @DisplayName("Should throw Exception when Transaction is not allowed")
    void createTransactionCase2() throws Exception {
        Account sender = new Account(
                1L,
                "eltin",
                new BigDecimal(100.0),
                LocalDate.parse("2000-09-09" ,DateTimeFormatter.ISO_LOCAL_DATE),
                "11450434452",
                123L,
                321L,
                AccountType.active);

        Account receiver = new Account(
                2L,
                "Maria",
                new BigDecimal(100.0),
                LocalDate.parse("2000-09-09" ,DateTimeFormatter.ISO_LOCAL_DATE),
                "65602501487",
                321L,
                321L,
                AccountType.active);

        when(accountService.findAccountsById(1L)).thenReturn(sender);
        when(accountService.findAccountsById(2L)).thenReturn(receiver);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDto request = new TransactionDto(
                    new BigDecimal(1000),
                    new BigDecimal(100),
                    2L,
                    1L,
                    LocalDate.now(),
                    TransactionType.transactionForUser);
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());
    }
}