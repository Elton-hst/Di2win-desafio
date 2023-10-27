package com.di2win.domin.service;

import com.di2win.domin.entity.Transaction;
import com.di2win.domin.entity.Account;
import com.di2win.domin.entity.enums.TransactionType;
import com.di2win.web.dto.TransactionDto;
import com.di2win.infra.repository.TransactionRepository;
import com.di2win.infra.repository.AccountRepository;
import com.di2win.web.dto.mapper.TransactionMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TransactionService {
    private final AccountService accountService;
    private final TransactionRepository repository;
    public TransactionService(AccountService accountService, TransactionRepository repository) {
        this.accountService = accountService;
        this.repository = repository;
    }

    private BigDecimal limit = BigDecimal.valueOf(2000);
    public void transactionLimitToDay(BigDecimal transaction) throws Exception{
        if(transaction.compareTo(limit) > 0) {
            throw new Exception("o valor excede o limite diário");
        }
        if(transaction.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("o valor da conta não pode ser negativo");
        }
    }

    public List<Transaction> statement(Account account, LocalDateTime from, LocalDateTime until) throws Exception{
        List<Transaction> transactions;
        Account userFromDatabase = accountService.getByAgencyAndAccount(account.getAgency(), account.getAccount());

        if (from == null || until == null){
            transactions = repository.getAllTransaction(userFromDatabase);
        }else{
            transactions = repository.getAllTransactionByPeriod(userFromDatabase, from, until);
        }

        return transactions;
    }

    public Transaction withdraw(TransactionDto transaction) throws Exception {
        Account reciver = this.accountService.getByAgencyAndAccount(transaction.agency(), transaction.account());
        Transaction newTransaction = TransactionMapper.toEntity(transaction, reciver);
        newTransaction.setTransactionType(TransactionType.withdraw);

        transactionLimitToDay(transaction.value());
        if(reciver.getBalance().subtract(transaction.value()).compareTo(BigDecimal.ZERO) <0) {
            throw new Exception("o saldo da conta não pode ser negativo");
        }
        reciver.setBalance(reciver.getBalance().subtract(transaction.value()));

        this.repository.save(newTransaction);
        this.accountService.saveAccount(reciver);

        return newTransaction;
    }

    public Transaction deposit(TransactionDto transaction) throws Exception{
        Account reciver = this.accountService.getByAgencyAndAccount(transaction.agency(), transaction.account());
        Transaction newTransaction = TransactionMapper.toEntity(transaction, reciver);
        newTransaction.setTransactionType(TransactionType.deposit);

        reciver.setBalance(reciver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.accountService.saveAccount(reciver);

        return newTransaction;
    }

    public Transaction createTransaction(TransactionDto transaction) throws Exception {
        Account sender = this.accountService.getByAgencyAndAccount(transaction.agency(), transaction.account());
        Account reciver = this.accountService.getByAgencyAndAccount(transaction.agency(), transaction.account());

        accountService.validationTransaction(reciver, transaction.value());

        Transaction newTransaction = TransactionMapper.toEntity(transaction, reciver);
        newTransaction.setTransactionType(TransactionType.transactionForUser);
        newTransaction.setSender(sender);

        transactionLimitToDay(transaction.value());
        if(sender.getBalance().subtract(transaction.value()).compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("o saldo da conta não pode ser negativo");
        }
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        reciver.setBalance(reciver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.accountService.saveAccount(sender);
        this.accountService.saveAccount(reciver);

        return newTransaction;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

}