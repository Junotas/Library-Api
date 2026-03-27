package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.LoanDto;
import com.ludvig.libraryapi.dto.LoanRequest;
import com.ludvig.libraryapi.entity.Book;
import com.ludvig.libraryapi.entity.Borrower;
import com.ludvig.libraryapi.entity.Loan;
import com.ludvig.libraryapi.exception.ResourceNotFoundException;
import com.ludvig.libraryapi.mapper.LoanMapper;
import com.ludvig.libraryapi.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

  private final LoanRepository loanRepository;
  private final BookService bookService;
  private final BorrowerService borrowerService;

  public LoanService(
      LoanRepository loanRepository, BookService bookService, BorrowerService borrowerService) {
    this.loanRepository = loanRepository;
    this.bookService = bookService;
    this.borrowerService = borrowerService;
  }

  public List<LoanDto> findAll() {
    return loanRepository.findAll().stream().map(LoanMapper::toDto).toList();
  }

  public LoanDto findById(Long id) {
    Loan loan =
        loanRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    return LoanMapper.toDto(loan);
  }

  public LoanDto save(LoanRequest request) {
    Book book = bookService.findEntityById(request.bookId());
    Borrower borrower = borrowerService.findEntityById(request.borrowerId());
    Loan loan = new Loan();
    loan.setLoanDate(request.loanDate());
    loan.setBook(book);
    loan.setBorrower(borrower);
    Loan saved = loanRepository.save(loan);
    return LoanMapper.toDto(saved);
  }

  public LoanDto update(Long id, LoanRequest request) {
    Loan loan =
        loanRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    Book book = bookService.findEntityById(request.bookId());
    Borrower borrower = borrowerService.findEntityById(request.borrowerId());
    loan.setLoanDate(request.loanDate());
    loan.setBook(book);
    loan.setBorrower(borrower);
    Loan saved = loanRepository.save(loan);
    return LoanMapper.toDto(saved);
  }

  public void delete(Long id) {
    loanRepository.deleteById(id);
  }
}
