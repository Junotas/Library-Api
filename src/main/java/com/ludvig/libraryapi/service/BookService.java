package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.BookDto;
import com.ludvig.libraryapi.entity.Book;
import com.ludvig.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<BookDto> findAll() {
    return bookRepository.findAll().stream()
        .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor().getId()))
        .toList();
  }

  public BookDto findById(Long id) {
    Book book = bookRepository.findById(id).orElseThrow();
    return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getId());
  }
}
