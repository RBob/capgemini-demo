package com.pboc.demo.controller;

import com.pboc.demo.exception.CustomerNotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom {@link ResponseEntityExceptionHandler} that returns a {@link ProblemDetail} in case of
 * exception.
 * Refer to rfc <a href="https://www.rfc-editor.org/rfc/rfc9457.html">rfc9457</a>
 */
@Slf4j
@RestControllerAdvice
public class ProblemDetailsExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Customize the handling of {@link MethodArgumentNotValidException}. This method generates a
   * {@link ProblemDetail} response.
   *
   * @param e the exception to handle
   * @param headers the headers to be written to the response
   * @param status the selected response status
   * @param request the current request
   * @return a {@link ResponseEntity} with a {@link ProblemDetail} body
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    String details = getExceptionDetails(e);
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(e.getStatusCode(), details);
    return ResponseEntity.status(status.value()).body(problemDetail);
  }

  /**
   * Handle the {@link CustomerNotFoundException}
   *
   * @param e the exception to handle
   * @return a {@link ProblemDetail}
   */
  @ExceptionHandler(CustomerNotFoundException.class)
  public ProblemDetail handleCustomerNotFoundException(CustomerNotFoundException e) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    problemDetail.setInstance(URI.create("customer"));
    return problemDetail;
  }

  /**
   * Exception sink. Handle all the other exceptions raised within Spring MVC handling of the
   * request.
   *
   * @param e the exception to handle
   * @return a {@link ProblemDetail}
   */
  @ExceptionHandler(Exception.class)
  public ProblemDetail handleEveryOtherException(Exception e) {

    String exceptionId = UUID.randomUUID().toString();

    log.error("Caught exception id {}", exceptionId, e);
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Please contact the helpdesk using id " + exceptionId);
    problemDetail.setInstance(URI.create("customer"));
    return problemDetail;
  }

  /**
   * Extract all validation errors from the exception.
   *
   * @param e the exception
   * @return a String containing the validation errors
   */
  private String getExceptionDetails(MethodArgumentNotValidException e) {
    return Optional.of(e.getDetailMessageArguments())
        .map(
            args ->
                Arrays.stream(args)
                    .filter(msg -> !ObjectUtils.isEmpty(msg))
                    .reduce("Invalid request. ", (a, b) -> a + " " + b))
        .orElse("")
        .toString();
  }
}
