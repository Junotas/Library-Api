package com.ludvig.libraryapi.mapper;

import com.ludvig.libraryapi.dto.BorrowerDto;
import com.ludvig.libraryapi.dto.BorrowerRequest;
import com.ludvig.libraryapi.entity.Borrower;

public class BorrowerMapper {

  public static BorrowerDto toDto(Borrower borrower) {
    return new BorrowerDto(
        borrower.getId(),
        borrower.getName(),
        borrower.getEmail(),
        borrower.getLoans() != null ? borrower.getLoans().size() : 0);
  }

  public static Borrower toEntity(BorrowerRequest request) {
    Borrower borrower = new Borrower();
    borrower.setName(request.name());
    borrower.setEmail(request.email());
    return borrower;
  }
}
