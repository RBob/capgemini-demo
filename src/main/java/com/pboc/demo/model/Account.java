package com.pboc.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Entity
@Data
public class Account {

  @Id @GeneratedValue private Long accountId;

  private Long customerId;
  private BigDecimal balance;

  @OneToMany(fetch = FetchType.EAGER)
  private Set<Transaction> transactions = new HashSet<>();
}
