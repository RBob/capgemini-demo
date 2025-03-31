package com.pboc.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Entity
@Data
public class Customer {

  @Id private Long customerId;

  private String name;
  private String surname;

  @OneToMany(fetch = FetchType.EAGER)
  private Set<Account> accounts = new HashSet<>();
}
