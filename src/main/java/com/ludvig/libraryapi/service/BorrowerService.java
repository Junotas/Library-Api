package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.BorrowerDto;
import com.ludvig.libraryapi.dto.BorrowerRequest;
import com.ludvig.libraryapi.entity.Borrower;
import com.ludvig.libraryapi.exception.ResourceNotFoundException;
import com.ludvig.libraryapi.mapper.BorrowerMapper;
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
    return borrowerRepository.findAll().stream().map(BorrowerMapper::toDto).toList();
  }

  public BorrowerDto findById(Long id) {
    Borrower borrower = findEntityById(id);
    return BorrowerMapper.toDto(borrower);
  }

  public Borrower findEntityById(Long id) {
    return borrowerRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));
  }

  public BorrowerDto save(BorrowerRequest request) {
    Borrower borrower = BorrowerMapper.toEntity(request);
    Borrower saved = borrowerRepository.save(borrower);
    return BorrowerMapper.toDto(saved);
  }

  public BorrowerDto update(Long id, BorrowerRequest request) {
    Borrower borrower = findEntityById(id);
    borrower.setName(request.name());
    borrower.setEmail(request.email());
    Borrower saved = borrowerRepository.save(borrower);
    return BorrowerMapper.toDto(saved);
  }

  public void delete(Long id) {
    borrowerRepository.deleteById(id);
  }
}
