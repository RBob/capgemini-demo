package com.pboc.demo.controller.v1;

import static com.pboc.demo.controller.v1.RestConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import com.pboc.demo.controller.v1.converter.CustomerConverter;
import com.pboc.demo.controller.v1.dto.CustomerDTO;
import com.pboc.demo.model.Customer;
import com.pboc.demo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(V1_CUSTOMER_BASE_URL)
public class CustomerControllerImpl implements CustomerController {

  @Autowired private CustomerConverter customerConverter;
  @Autowired private CustomerService customerService;

  @GetMapping(
      value = V1_GET_CUSTOMER_RELATIVE_URL,
      produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
  public ResponseEntity<CustomerDTO> findCustomer(@PathVariable Long id) {
    Customer customer = customerService.findCustomer(id);
    return ResponseEntity.ok(customerConverter.convert(customer));
  }
}
