package com.di2win.controller;

import com.di2win.entity.Account;
import com.di2win.dto.AccountDto;
import com.di2win.entity.AccountType;
import com.di2win.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/blockAccount", method = RequestMethod.GET)
    public ResponseEntity<AccountType> blockAccount(@RequestBody AccountDto account) throws Exception{
        AccountType newAccount = accountService.blockAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }
    @RequestMapping(value = "/checkAccount", method = RequestMethod.GET)
    public ResponseEntity<Account> checkAccount(@RequestBody AccountDto account) throws Exception{
        Account newAccount = accountService.checkAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }
    @RequestMapping(value = "/creatAccount", method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto account) throws Exception {
        Account newAccount = accountService.createAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/getAllAccount", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAllAccount() {
        List<Account> Account = accountService.getAllAccount();
        return new ResponseEntity<>(Account, HttpStatus.OK);
    }

}
