package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.BorrowerDto;
import com.ludvig.libraryapi.dto.BorrowerRequest;
import com.ludvig.libraryapi.entity.Borrower;
import com.ludvig.libraryapi.exception.ResourceNotFoundException;
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
    Borrower borrower =
        borrowerRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));
    return new BorrowerDto(borrower.getId(), borrower.getName(), borrower.getEmail());
  }

  public BorrowerDto save(BorrowerRequest request) {
    Borrower borrower = new Borrower();
    borrower.setName(request.name());
    borrower.setEmail(request.email());
    Borrower saved = borrowerRepository.save(borrower);
    return new BorrowerDto(saved.getId(), saved.getName(), saved.getEmail());
  }

  public BorrowerDto update(Long id, BorrowerRequest request) {
    Borrower borrower =
        borrowerRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));
    borrower.setName(request.name());
    borrower.setEmail(request.email());
    Borrower saved = borrowerRepository.save(borrower);
    return new BorrowerDto(saved.getId(), saved.getName(), saved.getEmail());
  }

  public void delete(Long id) {
    borrowerRepository.deleteById(id);
  }
}
