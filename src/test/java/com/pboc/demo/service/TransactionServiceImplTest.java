package com.pboc.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pboc.demo.model.Account;
import com.pboc.demo.model.Transaction;
import com.pboc.demo.repository.TransactionRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

  @Mock TransactionRepository transactionRepository;

  @InjectMocks TransactionServiceImpl transactionService;

  @Test
  public void createTransaction() {

    Long customerId = 1L;
    Long accountId = 2L;
    BigDecimal initialCredit = BigDecimal.valueOf(5.10);

    Account account = new Account();
    account.setAccountId(accountId);
    account.setCustomerId(customerId);

    Transaction transaction = new Transaction();
    transaction.setAccountId(accountId);
    transaction.setAmount(initialCredit);

    when(transactionRepository.save(transaction)).thenReturn(transaction);

    Transaction result = transactionService.createTransaction(account, initialCredit);

    verify(transactionRepository).save(any(Transaction.class));

    assertEquals(result, transaction);
  }
}
