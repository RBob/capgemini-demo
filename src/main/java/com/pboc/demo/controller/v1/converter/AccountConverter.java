package com.pboc.demo.controller.v1.converter;

import com.pboc.demo.controller.v1.dto.AccountDTO;
import com.pboc.demo.controller.v1.dto.TransactionDTO;
import com.pboc.demo.model.Account;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<Account, AccountDTO> {

  @Autowired private TransactionConverter transactionConverter;

  @Override
  public AccountDTO convert(Account source) {

    Set<TransactionDTO> transactions =
        source.getTransactions().stream()
            .map(t -> transactionConverter.convert(t))
            .collect(Collectors.toSet());

    return new AccountDTO(
        source.getAccountId(), source.getCustomerId(), source.getBalance(), transactions);
  }
}
