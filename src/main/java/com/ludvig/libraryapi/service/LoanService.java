package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.LoanDto;
import com.ludvig.libraryapi.entity.Loan;
import com.ludvig.libraryapi.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

  private final LoanRepository loanRepository;

  public LoanService(LoanRepository loanRepository) {
    this.loanRepository = loanRepository;
  }

  public List<LoanDto> findAll() {
    return loanRepository.findAll().stream()
        .map(
            loan ->
                new LoanDto(
                    loan.getId(),
                    loan.getLoanDate(),
                    loan.getBook().getId(),
                    loan.getBorrower().getId()))
        .toList();
  }

  public LoanDto findById(Long id) {
    Loan loan = loanRepository.findById(id).orElseThrow();
    return new LoanDto(
        loan.getId(), loan.getLoanDate(), loan.getBook().getId(), loan.getBorrower().getId());
  }
}
