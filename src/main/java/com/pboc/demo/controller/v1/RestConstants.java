package com.pboc.demo.controller.v1;

public class RestConstants {

  private RestConstants() {}

  public static final String V1_ACCOUNT_BASE_URL = "/v1/pboc/demo/account";

  public static final String V1_CUSTOMER_BASE_URL = "/v1/pboc/demo/customer";
  public static final String V1_GET_CUSTOMER_RELATIVE_URL = "/{id}";
  public static final String V1_GET_CUSTOMER_URL =
      V1_CUSTOMER_BASE_URL + V1_GET_CUSTOMER_RELATIVE_URL;
}
