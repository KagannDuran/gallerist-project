package com.kagan.starter.service.concrete;

import com.kagan.starter.dto.account.AccountDto;
import com.kagan.starter.dto.account.AccountDtoIU;
import com.kagan.starter.entity.Account;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.mapper.AccountMapper;
import com.kagan.starter.repository.AccountRepository;
import com.kagan.starter.service.abstracts.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountManager implements IAccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountManager(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDto saveAccount(AccountDtoIU accountDtoIU) {
        Account account = accountMapper.AccountDtoIUtoEntity(accountDtoIU);
        accountRepository.save(account);
        return accountMapper.EntitytoAccountDto(account);

    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<Account> account = accountRepository.findAll();
        return account.stream().map(value ->accountMapper.EntitytoAccountDto(value)).collect(Collectors.toList());
    }

    @Override
    public AccountDto getByAccountId(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));

        return accountMapper.EntitytoAccountDto(account);
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDtoIU accountDtoIU) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

        accountMapper.updateAccountFromDto(accountDtoIU,account);

        return  accountMapper.EntitytoAccountDto(accountRepository.save(account));
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));
        accountRepository.delete(account);

    }
}
