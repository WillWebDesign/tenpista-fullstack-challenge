package com.tenpista.backend.api;

import com.tenpista.backend.api.dto.*;
import com.tenpista.backend.domain.Transaction;
import com.tenpista.backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        Transaction transaction = transactionService.create(request);

        TransactionResponse response = new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getMerchant(),
                transaction.getTenpistaName(),
                transaction.getTransactionDate(),
                transaction.getCreatedAt()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
