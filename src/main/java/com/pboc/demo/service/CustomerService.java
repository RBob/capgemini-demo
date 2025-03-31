package com.pboc.demo.service;

import com.pboc.demo.exception.CustomerNotFoundException;
import com.pboc.demo.model.Account;
import com.pboc.demo.model.Customer;

public interface CustomerService {

  Customer findCustomer(Long customerId) throws CustomerNotFoundException;

  void addAccount(Customer customer, Account account);
}
