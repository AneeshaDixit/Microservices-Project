package com.nagarro.customer_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long customerId;
    
    @Column(name = "user_name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
//  @Transient
//  private List<AccountModel> account =new ArrayList<>();

  
}
