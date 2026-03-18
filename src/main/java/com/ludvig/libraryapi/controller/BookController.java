package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.BookDto;
import com.ludvig.libraryapi.dto.BookRequest;
import com.ludvig.libraryapi.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<List<BookDto>> findAll() {
    return ResponseEntity.ok(bookService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(bookService.findById(id));
  }

  @PostMapping
  public ResponseEntity<BookDto> save(@RequestBody BookRequest request) {
    return ResponseEntity.status(201).body(bookService.save(request));
  }
}
