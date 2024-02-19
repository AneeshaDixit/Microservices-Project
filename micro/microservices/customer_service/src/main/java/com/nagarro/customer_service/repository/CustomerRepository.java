package com.nagarro.customer_service.repository;

import com.nagarro.customer_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<User, Long> {
    // Add custom query methods if needed

    // Example of a custom query method:
    // List<CustomerDTO> findBySomeCondition(String someCondition);
}
