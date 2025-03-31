package com.pboc.demo.controller.v1.converter;

import com.pboc.demo.controller.v1.dto.AccountDTO;
import com.pboc.demo.controller.v1.dto.CustomerDTO;
import com.pboc.demo.model.Customer;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter implements Converter<Customer, CustomerDTO> {

  @Autowired private AccountConverter accountConverter;

  @Override
  public CustomerDTO convert(Customer source) {

    Set<AccountDTO> accounts =
        source.getAccounts().stream()
            .map(a -> accountConverter.convert(a))
            .collect(Collectors.toSet());

    return new CustomerDTO(source.getCustomerId(), source.getName(), source.getSurname(), accounts);
  }
}
