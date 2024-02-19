package com.nagarro.account_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nagarro.account_service.accountdto.AccountDTO;
import com.nagarro.account_service.service.AccountService;
import java.util.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/newAccount")
    public ResponseEntity<AccountDTO> newAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO updatedAccount = accountService.newAccount(accountDTO);
        return new ResponseEntity<>(updatedAccount, HttpStatus.CREATED);
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<AccountDTO> depositToAccountById(@PathVariable Long accountId, @RequestParam Double amount) {
        AccountDTO updatedAccount = accountService.depositToAccountById(accountId , amount);
        return updatedAccount != null
                ? new ResponseEntity<>(updatedAccount, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<AccountDTO> withdrawFromAccount(@PathVariable Long accountId, @RequestParam Double amount ) {
        AccountDTO updatedAccount = accountService.withdrawFromAccount(accountId, amount);
        return updatedAccount != null
                ? new ResponseEntity<>(updatedAccount, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
   
    @GetMapping("/info/{accountId}")
    public ResponseEntity<AccountDTO> getAccountDetails(@PathVariable Long accountId) {
        AccountDTO account = accountService.getAccountDetails(accountId);
        return account != null
                ? new ResponseEntity<>(account, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/info/user/{userId}")
    public ResponseEntity<AccountDTO> getAccountDetailsByUserId(@PathVariable Long userId) {
    	 AccountDTO account = accountService.getAccountDetailsByUserId(userId);
         System.out.println(userId);
         return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  

    
    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @DeleteMapping("/deleteByUserId/{userId}")
    public ResponseEntity<Void> deleteByUserId(@PathVariable Long userId) {
        accountService.deleteByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
