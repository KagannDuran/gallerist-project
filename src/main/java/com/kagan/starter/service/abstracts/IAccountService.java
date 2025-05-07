package com.kagan.starter.service.abstracts;

import com.kagan.starter.dto.account.AccountDto;
import com.kagan.starter.dto.account.AccountDtoIU;

import java.util.List;

public interface IAccountService {

    public AccountDto saveAccount(AccountDtoIU accountDtoIU);
    public List<AccountDto> getAllAccount();
    public AccountDto getByAccountId(Long id);
    public AccountDto updateAccount(Long id,AccountDtoIU accountDtoIU);
    public void deleteAccount(Long id);
}
