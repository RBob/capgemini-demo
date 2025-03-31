package com.pboc.demo.controller.v1;

import static com.pboc.demo.controller.v1.RestConstants.V1_GET_CUSTOMER_URL;
import static com.pboc.demo.controller.v1.ValidationConstants.CUSTOMER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import com.pboc.demo.DemoApplication;
import com.pboc.demo.controller.v1.dto.CustomerDTO;
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
public class CustomerControllerIT {

  Long customerId = 1L;

  @Autowired TestRestTemplate restTemplate;

  @Test
  public void when_findCustomer_then_200_OK() {

    RequestEntity<Void> request =
        RequestEntity.get(V1_GET_CUSTOMER_URL, customerId)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .build();

    ResponseEntity<CustomerDTO> response = restTemplate.exchange(request, CustomerDTO.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    CustomerDTO customer = response.getBody();
    assertEquals(customerId, customer.customerId());
  }

  @Test
  public void when_findCustomer_withBadCustomerId_then_400_BAD_REQUEST() {

    String badCustomerId = "not a long value";

    RequestEntity<Void> request =
        RequestEntity.get(V1_GET_CUSTOMER_URL, badCustomerId)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .build();

    ResponseEntity<ProblemDetail> response = restTemplate.exchange(request, ProblemDetail.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void when_findNonExistingCustomer_then_404_NOT_FOUND() {

    Long nonExistingCustomerId = 7L;

    RequestEntity<Void> request =
        RequestEntity.get(V1_GET_CUSTOMER_URL, nonExistingCustomerId)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .build();

    ResponseEntity<ProblemDetail> response = restTemplate.exchange(request, ProblemDetail.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    ProblemDetail problemDetail = response.getBody();
    assertTrue(problemDetail.getDetail().contains(CUSTOMER_NOT_FOUND));
  }

  @Test
  public void when_findCustomer_withWrongAccept_then_406_NOT_ACCEPTABLE() {

    RequestEntity<Void> request =
        RequestEntity.get(V1_GET_CUSTOMER_URL, customerId)
            .header(ACCEPT, APPLICATION_XML_VALUE)
            .build();

    ResponseEntity<ProblemDetail> response = restTemplate.exchange(request, ProblemDetail.class);
    assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
  }
}
