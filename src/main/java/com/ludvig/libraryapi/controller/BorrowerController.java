package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.BorrowerDto;
import com.ludvig.libraryapi.dto.BorrowerRequest;
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

  @PostMapping
  public ResponseEntity<BorrowerDto> save(@RequestBody BorrowerRequest request) {
    return ResponseEntity.status(201).body(borrowerService.save(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<BorrowerDto> update(
      @PathVariable Long id, @RequestBody BorrowerRequest request) {
    return ResponseEntity.ok(borrowerService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    borrowerService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
