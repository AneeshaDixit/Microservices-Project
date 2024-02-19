package com.nagarro.account_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.account_service.model.AccountModel;
import java.util.*;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {
	AccountModel findByUserId(Long UserId);
}
