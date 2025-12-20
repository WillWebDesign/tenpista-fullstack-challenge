package com.tenpista.backend.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TransactionResponse {

  private Long id;
  private Integer amount;
  private String merchant;
  private String tenpistaName;
  private LocalDateTime transactionDate;
  private LocalDateTime createdAt;
}
