package com.pboc.demo.controller.v1;

import com.pboc.demo.controller.v1.dto.OpenAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Account API")
public interface AccountController {

  @Operation(
      summary = "Open account for existing customer",
      description = "Opens an account and creates a transaction if initial credit is positive.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Account created"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  ResponseEntity<Void> openAccount(OpenAccountDTO openAccountDTO);
}
