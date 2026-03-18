package com.ludvig.libraryapi.dto;

import java.time.LocalDate;

public record LoanDto(Long id, LocalDate loanDate, Long bookId, Long borrowerId) {}
