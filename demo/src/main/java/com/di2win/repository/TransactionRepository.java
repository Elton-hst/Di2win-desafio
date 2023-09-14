package com.di2win.repository;

import com.di2win.entity.Account;
import com.di2win.entity.Transaction;
import com.di2win.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE (t.receiver = :account OR t.sender = :account) AND t.timestamp >= :from AND t.timestamp <= :until AND (t.transactionType = :transactionType OR :transactionType IS NULL)")
    List<Transaction> getAllTransactionByPeriod(@Param("account") Account account, @Param("from") LocalDateTime from, @Param("until") LocalDateTime until, @Param("transactionType") TransactionType transactionType);

    @Query("SELECT t FROM Transaction t WHERE (t.receiver = :account OR t.sender = :account) AND (t.transactionType = :transactionType OR :transactionType IS NULL)")
    List<Transaction> getAllTransaction(
            @Param("account") Account account,
            @Param("transactionType") TransactionType transactionType);


}
