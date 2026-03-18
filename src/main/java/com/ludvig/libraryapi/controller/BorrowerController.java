package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.BorrowerDto;
import com.ludvig.libraryapi.service.BorrowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {

  private final BorrowerService borrowerService;

  public BorrowerController(BorrowerService borrowerService) {
    this.borrowerService = borrowerService;
  }

  @GetMapping
  public ResponseEntity<List<BorrowerDto>> findAll() {
    return ResponseEntity.ok(borrowerService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BorrowerDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(borrowerService.findById(id));
  }
}
