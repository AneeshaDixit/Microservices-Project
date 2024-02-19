package com.nagarro.account_service.service;

import java.util.List;

import com.nagarro.account_service.accountdto.AccountDTO;

public interface AccountService {
	
    AccountDTO newAccount(AccountDTO accountDTO);
    AccountDTO withdrawFromAccount(Long accountId, Double amount);
    AccountDTO getAccountDetails(Long accountId);
    List<AccountDTO> getAllAccounts();
    AccountDTO depositToAccountById(Long accountId, Double amount);
    AccountDTO getAccountDetailsByUserId(Long userId);
    void deleteAccount(Long accountId);
   void deleteByUserId(Long userId);
}
