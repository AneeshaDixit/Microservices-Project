package com.nagarro.account_service.accountdto;





//import com.nagarro.customer_service.customerdto.CustomerDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {
	
	  private Long accountId;
	  private Double balance;
      private Long userId; 
      private UserDTO customerInfo;
}
