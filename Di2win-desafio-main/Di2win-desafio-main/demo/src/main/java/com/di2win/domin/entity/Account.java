package com.di2win.domin.entity;

import com.di2win.domin.entity.enums.AccountType;
import com.di2win.web.dto.AccountDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal balance;
    private LocalDate birth;
    @Column(unique = true)
    private String document;
    private Long account;
    private Long agency;
    @NonNull
    private AccountType accountType;

    public Account(AccountDto data) {
        this.name = data.name();
        this.balance = data.balance();
        this.birth = data.birth();
        this.document = data.document();
        this.account = data.account();
        this.agency = data.agency();
        this.accountType = data.accountType();
    }
}
