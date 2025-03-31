package com.pboc.demo.controller.v1;

import static com.pboc.demo.controller.v1.RestConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import com.pboc.demo.controller.v1.dto.OpenAccountDTO;
import com.pboc.demo.model.Account;
import com.pboc.demo.service.AccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(V1_ACCOUNT_BASE_URL)
public class AccountControllerImpl implements AccountController {

  @Autowired private AccountService accountService;

  @PostMapping(
      consumes = APPLICATION_JSON_VALUE,
      produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
  public ResponseEntity<Void> openAccount(@Valid @RequestBody OpenAccountDTO openAccountDTO) {
    Account account =
        accountService.openAccount(openAccountDTO.customerId(), openAccountDTO.initialCredit());
    log.info("Created account {}", account.getAccountId());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
