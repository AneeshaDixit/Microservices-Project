package com.nagarro.customer_service.customerdto;

//import com.nagarro.customer_service.customerdto.CustomerDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	
	 private Long accountId;
	    private Double balance;
      private Long customerId; 
}
