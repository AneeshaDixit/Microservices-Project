package com.nagarro.account_service.model;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;
@Entity
@Table(name="BankAccount")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountModel {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="Account_number")
    private Long accountId;
    @Column(name="balance")
    private Double balance;
    
    private Long userId;
    

//    @Transient
//    User user;

    
}
