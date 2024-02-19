package com.nagarro.customer_service.service;

import com.nagarro.customer_service.customerdto.CustomerDTO;

import java.util.List;

public interface CustomerService {
   CustomerDTO addCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO updateCustomer(Long id, CustomerDTO updatedCustomerDTO);
    
    void deleteCustomer(Long id);
}

