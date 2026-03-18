package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.LoanDto;
import com.ludvig.libraryapi.dto.LoanRequest;
import com.ludvig.libraryapi.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

  private final LoanService loanService;

  public LoanController(LoanService loanService) {
    this.loanService = loanService;
  }

  @GetMapping
  public ResponseEntity<List<LoanDto>> findAll() {
    return ResponseEntity.ok(loanService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<LoanDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(loanService.findById(id));
  }

  @PostMapping
  public ResponseEntity<LoanDto> save(@RequestBody LoanRequest request) {
    return ResponseEntity.status(201).body(loanService.save(request));
  }
}
