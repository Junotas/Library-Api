package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.AuthorDto;
import com.ludvig.libraryapi.dto.AuthorRequest;
import com.ludvig.libraryapi.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping
  public ResponseEntity<List<AuthorDto>> findAll() {
    return ResponseEntity.ok(authorService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(authorService.findById(id));
  }

  @PostMapping
  public ResponseEntity<AuthorDto> save(@RequestBody AuthorRequest request) {
    return ResponseEntity.status(201).body(authorService.save(request));
  }
}
