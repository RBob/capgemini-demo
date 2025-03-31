package com.pboc.demo.service;

import com.pboc.demo.exception.CustomerNotFoundException;
import com.pboc.demo.model.Account;
import java.math.BigDecimal;

public interface AccountService {

  Account openAccount(Long customerId, BigDecimal initialCredit) throws CustomerNotFoundException;
}
