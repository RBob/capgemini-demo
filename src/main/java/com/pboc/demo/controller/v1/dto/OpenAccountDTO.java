package com.pboc.demo.controller.v1.dto;

import static com.pboc.demo.controller.v1.ValidationConstants.CUSTOMER_ID_NOT_NULL;
import static com.pboc.demo.controller.v1.ValidationConstants.POSITIVE_INITIAL_CREDIT;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record OpenAccountDTO(
    @NotNull(message = CUSTOMER_ID_NOT_NULL)
        @Schema(name = "customerId", example = "1", required = true)
        Long customerId,
    @PositiveOrZero(message = POSITIVE_INITIAL_CREDIT)
        @Schema(name = "initialCredit", example = "5.10")
        BigDecimal initialCredit) {}
