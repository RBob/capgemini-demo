package com.pboc.demo.service;

import static com.pboc.demo.controller.v1.ValidationConstants.CUSTOMER_NOT_FOUND;

import com.pboc.demo.exception.CustomerNotFoundException;
import com.pboc.demo.model.Account;
import com.pboc.demo.model.Customer;
import com.pboc.demo.repository.CustomerRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired private CustomerRepository customerRepository;

  @Transactional(readOnly = true)
  public Customer findCustomer(Long customerId) throws CustomerNotFoundException {
    Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
    if (optionalCustomer.isEmpty()) {
      log.warn(CUSTOMER_NOT_FOUND + " {}", customerId);
      throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND + customerId);
    }
    return optionalCustomer.get();
  }

  @Transactional
  public void addAccount(Customer customer, Account account) {
    customer.getAccounts().add(account);
    customerRepository.save(customer);
  }
}
