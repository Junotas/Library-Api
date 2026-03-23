package com.ludvig.libraryapi.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record LoanRequest(
    @NotNull(message = "Loan date is required") LocalDate loanDate,
    @NotNull(message = "Book ID is required") Long bookId,
    @NotNull(message = "Borrower ID is required") Long borrowerId) {}
