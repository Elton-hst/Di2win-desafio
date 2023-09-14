package com.di2win.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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
