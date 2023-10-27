package com.di2win.domin.service;

import com.di2win.domin.entity.Account;
import com.di2win.domin.entity.enums.AccountType;
import com.di2win.web.dto.AccountDto;
import com.di2win.infra.repository.AccountRepository;
import com.di2win.domin.service.utils.ValidateDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getByAgencyAndAccount(long agency, long account) throws Exception {
        return accountRepository.getByAgencyAndAccount(agency, account)
                .orElseThrow(() -> new Exception("agencia e conta não encontrada"));
    }

    public AccountType blockAccount(AccountDto data) throws Exception{
        Account conta = accountRepository.findAccountsByDocument(data.document())
                .orElseThrow(() -> new Exception("CPF não encontrado"));

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
        return accountRepository.findAccountsByDocument(data.document())
                .orElseThrow(() -> new Exception("CPF não encontrado"));
    }
    public Account findAccountsById(Long id) throws Exception {
        return accountRepository.findAccountsById(id)
                .orElseThrow(() ->  new Exception ("conta não encontrado"));
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
            throw new Exception("Transação não autorizada");
        }
    }
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }
}
