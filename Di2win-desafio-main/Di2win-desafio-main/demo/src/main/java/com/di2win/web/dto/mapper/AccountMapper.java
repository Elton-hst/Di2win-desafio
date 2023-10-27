package com.di2win.web.dto.mapper;

import com.di2win.web.dto.AccountDto;
import com.di2win.domin.entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountMapper {

    public Account toEntity(AccountDto dto) {
        if(dto == null) {
            return null;
        }
        Account account = new Account();
        account.setName(dto.name());
        account.setDocument(dto.document());
        account.setBalance(dto.balance());
        account.setBirth(dto.birth());
        account.setAccount(dto.account());
        account.setAgency(dto.agency());
        account.setAccountType(dto.accountType());
        return account;
    }

    public AccountDto toDto(Account account) {
        if(account == null) {
            return null;
        }
        return new AccountDto(
                account.getName(),
                account.getDocument(),
                account.getBalance(),
                account.getBirth(),
                account.getAccount(),
                account.getAgency(),
                account.getAccountType());
    }

}
