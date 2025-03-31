package com.pboc.demo.controller.v1;

import com.pboc.demo.controller.v1.dto.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Customer API")
public interface CustomerController {

  @Operation(summary = "Find a customer")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Returns the customer"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "406", description = "Media type not acceptable"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  ResponseEntity<CustomerDTO> findCustomer(
      @Parameter(name = "id", required = true, description = "Customer id", example = "1") Long id);
}
