package com.tenpista.backend.service;

import java.util.List;

import com.tenpista.backend.api.dto.CreateTransactionRequest;
import com.tenpista.backend.domain.Transaction;

public interface TransactionService {

  Transaction create(CreateTransactionRequest request);

  List<Transaction> findAll();

  void delete(Long id);
}
