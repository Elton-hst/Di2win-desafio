package com.di2win.service;

import com.di2win.dto.StatementDto;
import com.di2win.entity.Transaction;
import com.di2win.entity.Account;
import com.di2win.dto.TransactionDto;
import com.di2win.repository.TransactionRepository;
import com.di2win.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository repository;

    private BigDecimal limit = BigDecimal.valueOf(2000);
    public void transactionLimitToDay(BigDecimal transaction) throws Exception{
        if(transaction.compareTo(limit) > 0) {
            throw new Exception("o valor excede o limite diário");
        }
        if(transaction.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("o valor da conta não pode ser negativo");
        }
    }

    public List<Transaction> statement(Account account, LocalDateTime from, LocalDateTime until, Transaction transaction) throws Exception{
        List<Transaction> transactions;
        Optional<Account> userFromDatabase = accountRepository.getByAgencyAndAccount(account.getAgency(), account.getAccount());

        if(userFromDatabase.isEmpty()) {
            throw new Exception("Agencia ou conta invalida");
        }

        if (from == null || until == null){
            transactions = repository.getAllTransaction(userFromDatabase.get(), transaction.getTransactionType());
        }else{
            transactions = repository.getAllTransactionByPeriod(userFromDatabase.get(), from, until, transaction.getTransactionType());
        }

        return transactions;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction withdraw(TransactionDto transaction) throws Exception {
        Account reciver = this.accountService.findAccountsById(transaction.reciverId());

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setReceiver(reciver);
        newTransaction.setTimestamp(LocalDateTime.now());

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
        Account reciver = this.accountService.findAccountsById(transaction.reciverId());

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setReceiver(reciver);
        newTransaction.setTimestamp(LocalDateTime.now());

        reciver.setBalance(reciver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.accountService.saveAccount(reciver);

        return newTransaction;
    }

    public Transaction createTransaction(TransactionDto transaction) throws Exception {
        Account sender = this.accountService.findAccountsById(transaction.senderId());
        Account reciver = this.accountService.findAccountsById(transaction.reciverId());

        accountService.validationTransaction(reciver, transaction.value());

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(reciver);
        newTransaction.setTimestamp(LocalDateTime.now());

        transactionLimitToDay(transaction.value());
        if(sender.getBalance().subtract(transaction.value()).compareTo(BigDecimal.ZERO) <0) {
            throw new Exception("o saldo da conta não pode ser negativo");
        }
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        reciver.setBalance(reciver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.accountService.saveAccount(sender);
        this.accountService.saveAccount(reciver);

        return newTransaction;
    }

}