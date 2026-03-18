package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.BorrowerDto;
import com.ludvig.libraryapi.entity.Borrower;
import com.ludvig.libraryapi.repository.BorrowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerService {

  private final BorrowerRepository borrowerRepository;

  public BorrowerService(BorrowerRepository borrowerRepository) {
    this.borrowerRepository = borrowerRepository;
  }

  public List<BorrowerDto> findAll() {
    return borrowerRepository.findAll().stream()
        .map(borrower -> new BorrowerDto(borrower.getId(), borrower.getName(), borrower.getEmail()))
        .toList();
  }

  public BorrowerDto findById(Long id) {
    Borrower borrower = borrowerRepository.findById(id).orElseThrow();
    return new BorrowerDto(borrower.getId(), borrower.getName(), borrower.getEmail());
  }
}
