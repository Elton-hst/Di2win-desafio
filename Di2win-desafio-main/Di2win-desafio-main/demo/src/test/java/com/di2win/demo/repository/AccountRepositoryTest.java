package com.di2win.demo.repository;

import com.di2win.web.dto.AccountDto;
import com.di2win.domin.entity.Account;
import com.di2win.domin.entity.enums.AccountType;
import com.di2win.infra.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findUserByDocumentCase1() {
        AccountDto data = new AccountDto(
                "Fernanda",
                "11450434452",
                new BigDecimal(10),
                LocalDate.parse("2000-09-09", DateTimeFormatter.ISO_LOCAL_DATE),
                123L,
                321L,
                AccountType.active);
        this.createAccount(data);

        Optional<Account> result = this.accountRepository.findAccountsByDocument(data.document());

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User from DB when user not exists")
    void findUserByDocumentCase2() {
        String document = "99999999901";

        Optional<Account> result = this.accountRepository.findAccountsByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private Account createAccount(AccountDto data){
        Account account = new Account(data);
        this.entityManager.persist(account);
        return account;
    }
}