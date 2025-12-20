package com.tenpista.backend.service;

import com.tenpista.backend.api.dto.CreateTransactionRequest;
import com.tenpista.backend.domain.Transaction;

public interface TransactionService {

    Transaction create(CreateTransactionRequest request);

    void delete(Long id);
}
