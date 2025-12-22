package com.tenpista.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tenpista.backend.api.dto.CreateTransactionRequest;
import com.tenpista.backend.domain.Transaction;

public interface TransactionService {

  Transaction create(CreateTransactionRequest request);

  Page<Transaction> findAll(Pageable pageable);

  void delete(Long id);
}
