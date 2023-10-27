package com.di2win.domin.entity;

import com.di2win.domin.entity.enums.TransactionType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name= "sender_id")
    private Account sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiver;
    private TransactionType transactionType;
    private LocalDateTime timestamp;

}
