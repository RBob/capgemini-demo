package com.pboc.demo.service;

import com.pboc.demo.exception.CustomerNotFoundException;
import com.pboc.demo.model.Account;
import com.pboc.demo.model.Customer;
import com.pboc.demo.model.Transaction;
import com.pboc.demo.repository.AccountRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired private AccountRepository accountRepository;
  @Autowired private CustomerService customerService;
  @Autowired private TransactionService transactionService;

  @Transactional
  public Account openAccount(Long customerId, BigDecimal initialCredit)
      throws CustomerNotFoundException {

    Customer customer = customerService.findCustomer(customerId);
    Account account = account(customer);

    if (!initialCredit.equals(BigDecimal.ZERO)) {
      Transaction transaction = transactionService.createTransaction(account, initialCredit);
      addTransaction(account, transaction);
    }

    customerService.addAccount(customer, account);

    return account;
  }

  @Transactional
  protected Account account(Customer customer) {

    Account account = new Account();
    account.setCustomerId(customer.getCustomerId());
    account.setBalance(BigDecimal.ZERO);
    accountRepository.save(account);
    return account;
  }

  protected void addTransaction(Account account, Transaction transaction) {

    account.getTransactions().add(transaction);
    account.setBalance(transaction.getAmount());
  }
}
