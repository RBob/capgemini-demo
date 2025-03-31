package com.pboc.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.pboc.demo.exception.CustomerNotFoundException;
import com.pboc.demo.model.Account;
import com.pboc.demo.model.Customer;
import com.pboc.demo.model.Transaction;
import com.pboc.demo.repository.AccountRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

  @Mock AccountRepository accountRepository;
  @Mock CustomerService customerService;
  @Mock TransactionService transactionService;

  @Spy @InjectMocks AccountServiceImpl accountService;

  @Test
  void openAccount() {

    Long customerId = 1L;
    Long accountId = 2L;
    BigDecimal initialCredit = BigDecimal.valueOf(5.10);

    Customer customer = new Customer();
    customer.setCustomerId(customerId);

    Account account = new Account();
    account.setAccountId(accountId);
    account.setCustomerId(customerId);

    Transaction transaction = new Transaction();
    transaction.setAccountId(accountId);
    transaction.setAmount(initialCredit);

    when(customerService.findCustomer(customerId)).thenReturn(customer);
    when(accountService.account(customer)).thenReturn(account);
    when(transactionService.createTransaction(account, initialCredit)).thenReturn(transaction);
    doNothing().when(customerService).addAccount(customer, account);

    Account result = accountService.openAccount(customerId, initialCredit);

    verify(accountRepository).save(any(Account.class));

    assertEquals(result, account);
  }

  @Test
  void openAccountException() {

    Long customerId = 1L;
    BigDecimal initialCredit = BigDecimal.valueOf(5.10);

    doThrow(CustomerNotFoundException.class).when(customerService).findCustomer(customerId);

    assertThrows(
        CustomerNotFoundException.class,
        () -> accountService.openAccount(customerId, initialCredit),
        "Expected exception");
  }
}
