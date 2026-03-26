package com.ludvig.libraryapi.mapper;

import com.ludvig.libraryapi.dto.BookDto;
import com.ludvig.libraryapi.dto.BookRequest;
import com.ludvig.libraryapi.entity.Author;
import com.ludvig.libraryapi.entity.Book;

public class BookMapper {

  public static BookDto toDto(Book book) {
    return new BookDto(
        book.getId(), book.getTitle(), book.getAuthor().getId(), book.getAuthor().getName());
  }

  public static Book toEntity(BookRequest request, Author author) {
    Book book = new Book();
    book.setTitle(request.title());
    book.setAuthor(author);
    return book;
  }
}
