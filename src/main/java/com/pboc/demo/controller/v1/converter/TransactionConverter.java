package com.pboc.demo.controller.v1.converter;

import com.pboc.demo.controller.v1.dto.TransactionDTO;
import com.pboc.demo.model.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter implements Converter<Transaction, TransactionDTO> {

  @Override
  public TransactionDTO convert(Transaction source) {

    return new TransactionDTO(source.getTransactionId(), source.getAccountId(), source.getAmount());
  }
}
