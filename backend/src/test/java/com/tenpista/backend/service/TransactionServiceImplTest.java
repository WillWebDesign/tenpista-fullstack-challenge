package com.tenpista.backend.service;

import com.tenpista.backend.api.dto.CreateTransactionRequest;
import com.tenpista.backend.domain.Transaction;
import com.tenpista.backend.exception.BusinessException;
import com.tenpista.backend.repository.TransactionRepository;
import com.tenpista.backend.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionService;

  @Test
  void shouldCreateTransactionSuccessfully() {
    // Given
    CreateTransactionRequest request = new CreateTransactionRequest(
        1000,
        "Amazon",
        "Tenpista Test",
        LocalDateTime.now().minusMinutes(1));

    when(transactionRepository.save(any(Transaction.class)))
        .thenAnswer(invocation -> {
          Transaction t = invocation.getArgument(0);
          t.setId(1L);
          return t;
        });

    // When
    Transaction result = transactionService.create(request);

    // Then
    assertThat(result.getId()).isEqualTo(1L);
    assertThat(result.getAmount()).isEqualTo(1000);
    verify(transactionRepository).save(any(Transaction.class));
  }

  @Test
  void shouldFailWhenAmountIsNegative() {
    CreateTransactionRequest request = new CreateTransactionRequest(
        -100,
        "Test",
        "Fail",
        LocalDateTime.now());

    assertThatThrownBy(() -> transactionService.create(request))
        .isInstanceOf(BusinessException.class)
        .hasMessageContaining("amount");
  }

  @Test
  void shouldFailWhenDateIsInFuture() {
    CreateTransactionRequest request = new CreateTransactionRequest(
        100,
        "Test",
        "Fail",
        LocalDateTime.now().plusDays(1));

    assertThatThrownBy(() -> transactionService.create(request))
        .isInstanceOf(BusinessException.class)
        .hasMessageContaining("date");
  }

  @Test
  void shouldDeleteTransaction() {
    when(transactionRepository.existsById(1L)).thenReturn(true);

    transactionService.delete(1L);

    verify(transactionRepository).deleteById(1L);
  }

  @Test
  void shouldFailWhenDeletingNonExistingTransaction() {
    when(transactionRepository.existsById(99L)).thenReturn(false);

    assertThatThrownBy(() -> transactionService.delete(99L))
        .isInstanceOf(BusinessException.class)
        .hasMessageContaining("not found");
  }

  @Test
  void shouldReturnPagedTransactions() {
    Pageable pageable = PageRequest.of(0, 10);
    Transaction tx = new Transaction();
    tx.setId(1L);

    Page<Transaction> page = new PageImpl<>(List.of(tx), pageable, 1);

    when(transactionRepository.findAll(pageable)).thenReturn(page);

    Page<Transaction> result = transactionService.findAll(pageable);

    assertThat(result.getContent()).hasSize(1);
    verify(transactionRepository).findAll(pageable);
  }
}
