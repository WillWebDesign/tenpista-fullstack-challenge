package com.tenpista.backend.api;

import com.tenpista.backend.api.dto.*;
import com.tenpista.backend.api.error.ErrorResponse;
import com.tenpista.backend.domain.Transaction;
import com.tenpista.backend.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springdoc.core.annotations.ParameterObject;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.media.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @Operation(summary = "List all transactions")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "List of transactions", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Page.class)))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping
  public ResponseEntity<Page<TransactionResponse>> findAll(
      @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

    Page<TransactionResponse> response = transactionService.findAll(pageable)
        .map(t -> new TransactionResponse(
            t.getId(),
            t.getAmount(),
            t.getMerchant(),
            t.getTenpistaName(),
            t.getTransactionDate(),
            t.getCreatedAt()));

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Create a new transaction")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Transaction created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
      @ApiResponse(responseCode = "400", description = "Validation or business error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping
  public ResponseEntity<TransactionResponse> create(
      @Valid @RequestBody CreateTransactionRequest request) {
    Transaction transaction = transactionService.create(request);

    TransactionResponse response = new TransactionResponse(
        transaction.getId(),
        transaction.getAmount(),
        transaction.getMerchant(),
        transaction.getTenpistaName(),
        transaction.getTransactionDate(),
        transaction.getCreatedAt());

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  @Operation(summary = "Delete a transaction by id")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
      @ApiResponse(responseCode = "400", description = "Transaction not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    transactionService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
