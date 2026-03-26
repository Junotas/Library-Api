package com.ludvig.libraryapi.mapper;

import com.ludvig.libraryapi.dto.LoanDto;
import com.ludvig.libraryapi.entity.Loan;

public class LoanMapper {

  public static LoanDto toDto(Loan loan) {
    return new LoanDto(
        loan.getId(),
        loan.getLoanDate(),
        loan.getBook().getId(),
        loan.getBook().getTitle(),
        loan.getBorrower().getId(),
        loan.getBorrower().getName());
  }
}
