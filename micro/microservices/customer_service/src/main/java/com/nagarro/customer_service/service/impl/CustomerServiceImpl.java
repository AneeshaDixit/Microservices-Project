package com.nagarro.customer_service.service.impl;

import com.nagarro.customer_service.customerdto.CustomerDTO;
import com.nagarro.customer_service.exception.NoRecordFoundException;
import com.nagarro.customer_service.model.User;
import com.nagarro.customer_service.repository.CustomerRepository;
import com.nagarro.customer_service.service.CustomerService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private Logger logg = LoggerFactory.getLogger(CustomerServiceImpl.class); 
 
    private final WebClient.Builder deleteAccountWebClient;
    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        User customer = new User();
        
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        
        customer = modelMapper.map(customerDTO, User.class);
        customerRepository.save(customer);
        customerDTO = modelMapper.map(customer, CustomerDTO.class); 
        logg.info("Added customer with id: {}", customerDTO.getCustomerId());
        return customerDTO;
        
        
        // method if we dont want dto to show customerId
//        customerRepository.save(customer);
//        System.out.println(customerDTO);
//        return customerDTO;
    }
  


    // With Model mapper
    @Override
    public List<CustomerDTO> getAllCustomers() {
        //mapping users data into UserDTO
        return Arrays.asList(modelMapper.map(customerRepository.findAll(), CustomerDTO[].class));
    	
        
      /////  other-way  practice
        
//    	 List<User> customers = customerRepository.findAll();
//         return customers.stream()
//                 .map(customer -> modelMapper.map(customer, CustomerDTO.class))
//                 .collect(Collectors.toList());
    }


    @Override
    public CustomerDTO getCustomerById(Long id) {
        //mapping users data into UserDTO
    	 logg.info("Fetching customer by id: {}", id);
        return modelMapper.map(customerRepository.findById(id).orElseThrow(() -> new NoRecordFoundException()), CustomerDTO.class);
    }
//
//Without model mapper manually
//        @Override
//    public List<CustomerDTO> getAllCustomers() {
//        return customerRepository.findAll().stream()
//                .map(this::convertToDTO)  // Adjusted method reference
//                .collect(Collectors.toList());
//    }

    //    @Override
//    public CustomerDTO getCustomerById(Long id) {
//        return customerRepository.findById(id)
//                .map(this::convertToDTO)
//                .orElse(null);
//    }


    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO updatedCustomerDTO) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setName(updatedCustomerDTO.getName());
                    existingCustomer.setEmail(updatedCustomerDTO.getEmail());
                    existingCustomer.setPhone(updatedCustomerDTO.getPhone());
                    customerRepository.save(existingCustomer);
                    return convertToDTO(existingCustomer);
                })
                .orElse(null);
    }


    @Override
    public void deleteCustomer(Long id) {
    	
    	 
    	User customer= customerRepository.findById(id).orElseThrow(null);
       try {
    	  
    	   
    	
//        webClient
//        .delete()
//        .uri("http://BANK-SERVICE/account/deleteByUserId/" + customer.getCustomerId())
//         .retrieve()
//         .bodyToMono(Void.class)
//         .block();
        
    	   deleteAccountWebClient.build().delete()
    	   .uri("http://ACCOUNT-SERVICE/account/deleteByUserId/",uriBuilder -> uriBuilder
                   .path("/"+ customer.getCustomerId())
                   .build())
           .retrieve().bodyToMono(String.class).block();
        logg.info("Deleted customer with id: {}", customer.getCustomerId());
       }catch(ResourceAccessException e) {
    	   logg.error("Failed to send delete request to BANK-SERVICE. Error: {}", e.getMessage());
       }
       customerRepository.delete(customer);
       
       
        
    } 
    
    // Helper method to convert Customer entity to CustomerDTO
    private CustomerDTO convertToDTO(User customer) {
       return new CustomerDTO(customer.getCustomerId(), customer.getName(),customer.getEmail(),customer.getPhone());
    }



    @Autowired
    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            ModelMapper modelMapper,
            WebClient.Builder deleteAccountWebClient
    ) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.deleteAccountWebClient=deleteAccountWebClient;
    }
    
}