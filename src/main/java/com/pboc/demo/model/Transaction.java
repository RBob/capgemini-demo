package com.pboc.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Data
public class Transaction {

  @Id @GeneratedValue private Long transactionId;

  private Long accountId;
  private BigDecimal amount;
}
