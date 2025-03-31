package com.pboc.demo.controller.v1.dto;

import java.util.Set;

public record CustomerDTO(Long customerId, String name, String surname, Set<AccountDTO> accounts) {}
