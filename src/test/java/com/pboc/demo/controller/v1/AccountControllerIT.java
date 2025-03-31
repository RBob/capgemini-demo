package com.pboc.demo.controller.v1;

import static com.pboc.demo.controller.v1.RestConstants.*;
import static com.pboc.demo.controller.v1.ValidationConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.pboc.demo.DemoApplication;
import com.pboc.demo.controller.v1.dto.CustomerDTO;
import com.pboc.demo.controller.v1.dto.OpenAccountDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = DEFINED_PORT)
public class AccountControllerIT {

  Long customerId = 1L;

  @Autowired TestRestTemplate restTemplate;

  @Test
  public void when_openAccount_then_201_CREATED() {

    BigDecimal initialCredit = BigDecimal.valueOf(10.1);

    RequestEntity<OpenAccountDTO> accountRequest =
        RequestEntity.post(V1_ACCOUNT_BASE_URL)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .body(new OpenAccountDTO(customerId, initialCredit));

    ResponseEntity<Void> accountResponse = restTemplate.exchange(accountRequest, Void.class);

    assertEquals(HttpStatus.CREATED, accountResponse.getStatusCode());

    RequestEntity<Void> customerRequest =
        RequestEntity.get(V1_GET_CUSTOMER_URL, customerId)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .build();

    ResponseEntity<CustomerDTO> customerResponse =
        restTemplate.exchange(customerRequest, CustomerDTO.class);

    assertEquals(HttpStatus.OK, customerResponse.getStatusCode());

    CustomerDTO customer = customerResponse.getBody();
    assertEquals(customerId, customer.customerId());
  }

  @Test
  public void when_openAccount_withoutCustomerId_then_400_BAD_REQUEST() {

    RequestEntity<OpenAccountDTO> accountRequest =
        RequestEntity.post(V1_ACCOUNT_BASE_URL)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .body(new OpenAccountDTO(null, null));

    ResponseEntity<ProblemDetail> accountResponse =
        restTemplate.exchange(accountRequest, ProblemDetail.class);
    assertEquals(HttpStatus.BAD_REQUEST, accountResponse.getStatusCode());

    ProblemDetail problemDetail = accountResponse.getBody();
    assertTrue(problemDetail.getDetail().contains(CUSTOMER_ID_NOT_NULL));
  }

  @Test
  public void when_openAccount_withNegativeInitialCredit_then_400_BAD_REQUEST() {

    BigDecimal initialCredit = BigDecimal.valueOf(-10.1);

    RequestEntity<OpenAccountDTO> accountRequest =
        RequestEntity.post(V1_ACCOUNT_BASE_URL)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .body(new OpenAccountDTO(customerId, initialCredit));

    ResponseEntity<ProblemDetail> accountResponse =
        restTemplate.exchange(accountRequest, ProblemDetail.class);
    assertEquals(HttpStatus.BAD_REQUEST, accountResponse.getStatusCode());

    ProblemDetail problemDetail = accountResponse.getBody();
    assertTrue(problemDetail.getDetail().contains(POSITIVE_INITIAL_CREDIT));
  }

  @Test
  public void when_openAccount_withNonExistingCustomerId_then_404_NOT_FOUND() {

    Long nonExistingCustomerId = 7L;
    BigDecimal initialCredit = BigDecimal.valueOf(10.1);

    RequestEntity<OpenAccountDTO> accountRequest =
        RequestEntity.post(V1_ACCOUNT_BASE_URL)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .body(new OpenAccountDTO(nonExistingCustomerId, initialCredit));

    ResponseEntity<ProblemDetail> accountResponse =
        restTemplate.exchange(accountRequest, ProblemDetail.class);
    assertEquals(HttpStatus.NOT_FOUND, accountResponse.getStatusCode());

    ProblemDetail problemDetail = accountResponse.getBody();
    assertTrue(problemDetail.getDetail().contains(CUSTOMER_NOT_FOUND));
  }
}
