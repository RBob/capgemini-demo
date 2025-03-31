package com.pboc.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.pboc.demo.exception.CustomerNotFoundException;
import com.pboc.demo.model.Customer;
import com.pboc.demo.repository.CustomerRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

  @Mock CustomerRepository customerRepository;

  @InjectMocks CustomerServiceImpl customerService;

  @Test
  public void findCustomer() {

    Long customerId = 1L;

    Customer customer = new Customer();
    customer.setCustomerId(customerId);

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

    Customer result = customerService.findCustomer(customerId);

    assertEquals(result, customer);
  }

  @Test
  public void findCustomerException() {

    Long customerId = 1L;

    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    assertThrows(
        CustomerNotFoundException.class,
        () -> customerService.findCustomer(customerId),
        "Expected exception");
  }
}
