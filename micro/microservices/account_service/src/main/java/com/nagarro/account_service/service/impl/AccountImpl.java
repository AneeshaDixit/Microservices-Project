package com.nagarro.account_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.account_service.accountdto.AccountDTO;
import com.nagarro.account_service.accountdto.UserDTO;
import com.nagarro.account_service.model.AccountModel;
import com.nagarro.account_service.repository.AccountRepository;
import com.nagarro.account_service.service.AccountService;

import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.*;
@Service
public class AccountImpl implements AccountService {

    private final AccountRepository accountRepository;
    
    private final ModelMapper modelMapper;
    
    private final WebClient.Builder webClient;
    private final Logger logger = LoggerFactory.getLogger(AccountImpl.class);

    @Override
    public AccountDTO newAccount(AccountDTO accountDTO) {
        logger.info("Creating account with ID: {}", accountDTO.getUserId());
        AccountModel account = new AccountModel();
        account.setBalance(accountDTO.getBalance());
        account.setUserId(accountDTO.getUserId());
        accountRepository.save(account);
        UserDTO user = webClient.build().get()
                .uri("http://CUSTOMER-SERVICE/customers/" + account.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block(); 
        
        
        AccountDTO accountDTOs = modelMapper.map(account, AccountDTO.class);
        accountDTOs.setCustomerInfo(modelMapper.map(user, UserDTO.class));
        return accountDTOs;
        
        
        //for only details not dto ::
        ////return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public AccountDTO getAccountDetailsByUserId(Long userId) {
        logger.info("Fetching account details for user with ID: {}", userId);
        
        AccountModel account = accountRepository.findByUserId(userId);
        UserDTO user = webClient.build().get()
                .uri("http://CUSTOMER-SERVICE/customers/" + account.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setCustomerInfo(modelMapper.map(user, UserDTO.class));
        return accountDTO;
    }

    @Override
    public void deleteAccount(Long accountId) {
        logger.info("Deleting account with ID: {}", accountId);
        accountRepository.deleteById(accountId);
    }

    @Override
    public AccountDTO withdrawFromAccount(Long accountId, Double amount) {
        logger.info("Withdrawing {} from account with ID: {}", amount, accountId);
        AccountModel account = accountRepository.findById(accountId).orElse(null);
        
        UserDTO user = webClient.build().get()
                .uri("http://CUSTOMER-SERVICE/customers/" + account.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
     
        if (user != null) {

        if ( account != null  && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
           
        } else {
            logger.warn("Withdrawal failed for account with ID: {}", accountId);
            
        }
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setCustomerInfo(modelMapper.map(user, UserDTO.class));
        return accountDTO;
        
        }else return null;
        
        //else throw new  ResourceNotFoundException("Customer with given id not found try again with correct details !!");
    }

    @Override
    public AccountDTO getAccountDetails(Long accountId) {
        logger.info("Fetching account details to ID: {}", accountId);
//        return accountRepository.findById(accountId)
//                .map(account -> modelMapper.map(account, AccountDTO.class))
//                .orElse(null);
//        
//        webClient.get().uri("http://USER-SERVICE/customer/" + accountRepository.)
//        .retrieve()
//     .bodyToMono(User.class);
        AccountModel account = accountRepository.findById(accountId).orElse(null);
        
        // Fetch customer details using WebClient
        UserDTO user = webClient.build().get()
                .uri("http://CUSTOMER-SERVICE/customers/" + account.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();  // Blocking call to get the result, adjust as per your needs
        
        // Map Account and Customer details to AccountDTO
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setCustomerInfo(modelMapper.map(user, UserDTO.class));

        return accountDTO;
    }
        
        
        

    @Override
    public List<AccountDTO> getAllAccounts() {
        logger.info("Fetching all accounts");
        
        
        List<AccountModel> account = accountRepository.findAll();
        List<AccountDTO> accountDTOs = new ArrayList<>();
        
        
        for (AccountModel accounts: account) {
            UserDTO user = webClient.build().get()
                    .uri("http://CUSTOMER-SERVICE/customers/" + accounts.getUserId())
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();
            
            AccountDTO accountDTO = modelMapper.map(accounts, AccountDTO.class);
            accountDTO.setCustomerInfo(user);
            accountDTOs.add(accountDTO);
            
        }
        
        return accountDTOs;
    }
 
        
    //Practice Purpose 
        
        
       // without list of user(only one user not all )
//        UserDTO user = webClient.get()
//                .uri("http://localhost:8088/customers/" +account.getByUserId())
//                .retrieve()
//                .bodyToMono(UserDTO.class)
//                .block();
//        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
//        accountDTO.setCustomerInfo(modelMapper.map(user, UserDTO.class));
        
        //without userdto
    
//        return accountRepository.findAll()
//                .stream()
//                .map(account -> modelMapper.map(account, AccountDTO.class))
//                .collect(Collectors.toList());
    

    @Override
    public AccountDTO depositToAccountById(Long accountId, Double amount) {
        logger.info("Depositing {} to account with ID: {}", amount, accountId);
        AccountModel account = accountRepository.findById(accountId).orElse(null);
        
        UserDTO user = webClient.build().get()
                .uri("http://CUSTOMER-SERVICE/customers/" + account.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
                
     
        if (user != null && amount>0) {
            account.setBalance(account.getBalance() + amount);
            account.setUserId(account.getUserId());
            accountRepository.save(account);
            AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
            accountDTO.setCustomerInfo(modelMapper.map(user, UserDTO.class));
            return accountDTO;
        } else {
            logger.warn("Deposit failed!!!!");
            return null;
        }
        
        
        
    }

    @Override
    public void deleteByUserId(Long userId) {
        logger.info("Account deleted of : {}", userId);
        AccountModel account=accountRepository.findByUserId(userId);
        accountRepository.delete(account);
        
    }
    
    
    
    @Autowired
    public AccountImpl(
            AccountRepository accountRepository,
            ModelMapper modelMapper,
            WebClient.Builder webClient
    ) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.webClient=webClient;
    }
}
