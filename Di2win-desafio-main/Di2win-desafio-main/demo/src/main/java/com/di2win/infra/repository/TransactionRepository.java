package com.di2win.infra.repository;

import com.di2win.domin.entity.Account;
import com.di2win.domin.entity.Transaction;
import com.di2win.domin.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE (t.receiver = :account OR t.sender = :account) AND t.timestamp >= :from AND t.timestamp <= :until")
    List<Transaction> getAllTransactionByPeriod(
            @Param("account") Account account,
            @Param("from") LocalDateTime from,
            @Param("until") LocalDateTime until
    );

    @Query("SELECT t FROM Transaction t WHERE (t.receiver = :account)")
    List<Transaction> getAllTransaction(
            @Param("account") Account account
    );

}
