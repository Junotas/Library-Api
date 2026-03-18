package com.ludvig.libraryapi.dto;

import java.time.LocalDate;

public record LoanRequest(LocalDate loanDate, Long bookId, Long borrowerId) {}
