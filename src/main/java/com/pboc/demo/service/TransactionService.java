package com.pboc.demo.service;

import com.pboc.demo.model.Account;
import com.pboc.demo.model.Transaction;
import java.math.BigDecimal;

public interface TransactionService {

  Transaction createTransaction(Account account, BigDecimal initialCredit);
}
