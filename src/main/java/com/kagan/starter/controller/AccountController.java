package com.kagan.starter.controller;

import com.kagan.starter.dto.account.AccountDto;
import com.kagan.starter.dto.account.AccountDtoIU;
import com.kagan.starter.service.abstracts.IAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("rest/api/account")
public class AccountController {
    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDtoIU accountDtoIU)
    {
        AccountDto accountDto = accountService.saveAccount(accountDtoIU);
        return new ResponseEntity<>(accountDto, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountDto>> getAllAccount()
    {
        List<AccountDto> accountDto = accountService.getAllAccount();
        return new ResponseEntity<>(accountDto,HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id)
    {
        AccountDto accountDto = accountService.getByAccountId(id);
        return new ResponseEntity<>(accountDto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountDto> updateAccount(@Valid @PathVariable Long id , @RequestBody AccountDtoIU accountDtoIU )
    {
        AccountDto accountDto = accountService.updateAccount(id,accountDtoIU);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id)
    {
        accountService.deleteAccount(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
