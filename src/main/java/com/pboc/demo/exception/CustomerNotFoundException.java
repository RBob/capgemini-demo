package com.pboc.demo.exception;

/** Exception to be thrown when a {@link com.pboc.demo.model.Customer} is not found. */
public class CustomerNotFoundException extends RuntimeException {

  public CustomerNotFoundException(String message) {
    super(message);
  }
}
