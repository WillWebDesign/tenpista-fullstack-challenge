package com.tenpista.backend.service.impl;

import com.tenpista.backend.api.dto.CreateTransactionRequest;
import com.tenpista.backend.domain.Transaction;
import com.tenpista.backend.exception.BusinessException;
import com.tenpista.backend.repository.TransactionRepository;
import com.tenpista.backend.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  @Override
  @Transactional
  public Transaction create(CreateTransactionRequest request) {

    if (request.getAmount() <= 0) {
      throw new BusinessException("Transaction amount must be positive");
    }

    if (request.getTransactionDate().isAfter(LocalDateTime.now())) {
      throw new BusinessException("Transaction date cannot be in the future");
    }

    Transaction transaction = Transaction.builder()
        .amount(request.getAmount())
        .merchant(request.getMerchant())
        .tenpistaName(request.getTenpistaName())
        .transactionDate(request.getTransactionDate())
        .build();

    return transactionRepository.save(transaction);
  }

  @Override
  public Page<Transaction> findAll(Pageable pageable) {
    return transactionRepository.findAll(pageable);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!transactionRepository.existsById(id)) {
      throw new BusinessException("Transaction not found");
    }
    transactionRepository.deleteById(id);
  }
}
