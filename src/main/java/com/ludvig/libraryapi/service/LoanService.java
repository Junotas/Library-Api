package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.LoanDto;
import com.ludvig.libraryapi.dto.LoanRequest;
import com.ludvig.libraryapi.entity.Book;
import com.ludvig.libraryapi.entity.Borrower;
import com.ludvig.libraryapi.entity.Loan;
import com.ludvig.libraryapi.repository.BookRepository;
import com.ludvig.libraryapi.repository.BorrowerRepository;
import com.ludvig.libraryapi.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

  private final LoanRepository loanRepository;
  private final BookRepository bookRepository;
  private final BorrowerRepository borrowerRepository;

  public LoanService(
      LoanRepository loanRepository,
      BookRepository bookRepository,
      BorrowerRepository borrowerRepository) {
    this.loanRepository = loanRepository;
    this.bookRepository = bookRepository;
    this.borrowerRepository = borrowerRepository;
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

  public LoanDto save(LoanRequest request) {
    Book book = bookRepository.findById(request.bookId()).orElseThrow();
    Borrower borrower = borrowerRepository.findById(request.borrowerId()).orElseThrow();
    Loan loan = new Loan();
    loan.setLoanDate(request.loanDate());
    loan.setBook(book);
    loan.setBorrower(borrower);
    Loan saved = loanRepository.save(loan);
    return new LoanDto(
        saved.getId(), saved.getLoanDate(), saved.getBook().getId(), saved.getBorrower().getId());
  }

  public LoanDto update(Long id, LoanRequest request) {
    Loan loan = loanRepository.findById(id).orElseThrow();
    Book book = bookRepository.findById(request.bookId()).orElseThrow();
    Borrower borrower = borrowerRepository.findById(request.borrowerId()).orElseThrow();
    loan.setLoanDate(request.loanDate());
    loan.setBook(book);
    loan.setBorrower(borrower);
    Loan saved = loanRepository.save(loan);
    return new LoanDto(
        saved.getId(), saved.getLoanDate(), saved.getBook().getId(), saved.getBorrower().getId());
  }

  public void delete(Long id) {
    loanRepository.deleteById(id);
  }
}
