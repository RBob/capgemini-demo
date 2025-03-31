package com.pboc.demo.controller.v1.dto;

import java.math.BigDecimal;
import java.util.Set;

public record AccountDTO(
    Long accountId, Long customerId, BigDecimal balance, Set<TransactionDTO> transactions) {}
