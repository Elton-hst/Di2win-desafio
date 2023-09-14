package com.di2win.service;

import com.di2win.entity.Account;
import com.di2win.entity.AccountType;
import com.di2win.dto.AccountDto;
import com.di2win.repository.AccountRepository;
import com.di2win.service.utils.ValidateDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Executable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountType blockAccount(AccountDto data) throws Exception{
        Account conta = accountRepository.findAccountsByDocument(data.document()).orElseThrow(() -> new Exception("CPF não encontrado"));
        if(conta.getAccountType().equals(AccountType.active) || conta.getAccountType() == null) {
            conta.setAccountType(AccountType.blocked);
            accountRepository.save(conta);
            return conta.getAccountType();
        } else {
            throw new Exception("A conta já está bloqueada");
        }
    }
    public Account checkAccount(AccountDto data) throws Exception {
        boolean cpfValido = ValidateDocument.isCPF(data.document());
        if (!cpfValido) {
            throw new Exception("CPF invalido");
        }
        return accountRepository.findAccountsByDocument(data.document()).orElseThrow(() -> new Exception("CPF não encontrado"));
    }
    public Account findAccountsById(Long id) throws Exception {
        return accountRepository.findAccountsById(id).orElseThrow(() ->  new Exception ("conta não encontrado"));
    }
    public Account createAccount(AccountDto data) throws Exception {
        Account newAccount = new Account(data);
        boolean cpfValido = ValidateDocument.isCPF(data.document());
        if (!cpfValido) {
            throw new Exception("CPF invalido");
        } else {
            this.accountRepository.save(newAccount);
        }
        return newAccount;
    }
    public void validationTransaction(Account sender, BigDecimal amount) throws Exception{
        if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("valor insuficiente");
        }
    }
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }
}
