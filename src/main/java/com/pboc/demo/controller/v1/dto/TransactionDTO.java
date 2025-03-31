package com.pboc.demo.controller.v1.dto;

import java.math.BigDecimal;

public record TransactionDTO(Long transactionId, Long accountId, BigDecimal amount) {}
