package com.pboc.demo.service;

import com.pboc.demo.model.Account;
import com.pboc.demo.model.Transaction;
import com.pboc.demo.repository.TransactionRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired private TransactionRepository transactionRepository;

  @Transactional
  public Transaction createTransaction(Account account, BigDecimal initialCredit) {

    Transaction transaction = new Transaction();
    transaction.setAccountId(account.getAccountId());
    transaction.setAmount(initialCredit);
    return transactionRepository.save(transaction);
  }
}
