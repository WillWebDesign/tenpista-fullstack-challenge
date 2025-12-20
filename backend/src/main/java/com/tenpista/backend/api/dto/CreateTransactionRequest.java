package com.tenpista.backend.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

    @NotNull
    @Positive
    private Integer amount;

    @NotBlank
    private String merchant;

    @NotBlank
    private String tenpistaName;

    @NotNull
    private LocalDateTime transactionDate;
}
