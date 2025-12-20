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

import java.time.LocalDateTime;

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
}
