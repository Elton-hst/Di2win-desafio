package com.di2win.infra.repository;

import java.util.Optional;
import com.di2win.domin.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountsById(Long document);
    @Query("SELECT a FROM Account a WHERE a.account =:account AND a.agency =:agency")
    Optional<Account> getByAgencyAndAccount(long agency, long account);
    @Query("SELECT a FROM Account a WHERE a.document =:document")
    Optional<Account> findAccountsByDocument(String document);

}