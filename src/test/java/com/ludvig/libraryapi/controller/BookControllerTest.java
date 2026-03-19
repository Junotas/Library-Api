package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.AuthorDto;
import com.ludvig.libraryapi.dto.AuthorRequest;
import com.ludvig.libraryapi.dto.BookDto;
import com.ludvig.libraryapi.dto.BookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerTest {

  @Value("${server.port}")
  private int port;

  private final RestTemplate restTemplate = new RestTemplate();
  private Long authorId;

  @BeforeEach
  void setUp() {
    AuthorRequest authorRequest = new AuthorRequest("Test Author");
    ResponseEntity<AuthorDto> author =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/authors", authorRequest, AuthorDto.class);
    authorId = author.getBody().id();
  }

  @Test
  void shouldReturnEmptyListWhenNoBooks() {
    ResponseEntity<BookDto[]> response =
        restTemplate.getForEntity("http://localhost:" + port + "/books", BookDto[].class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void shouldCreateBook() {
    BookRequest request = new BookRequest("Harry Potter", authorId);

    ResponseEntity<BookDto> response =
        restTemplate.postForEntity("http://localhost:" + port + "/books", request, BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody().title()).isEqualTo("Harry Potter");
  }

  @Test
  void shouldGetBookById() {
    BookRequest request = new BookRequest("The Hobbit", authorId);
    ResponseEntity<BookDto> created =
        restTemplate.postForEntity("http://localhost:" + port + "/books", request, BookDto.class);

    ResponseEntity<BookDto> response =
        restTemplate.getForEntity(
            "http://localhost:" + port + "/books/" + created.getBody().id(), BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().title()).isEqualTo("The Hobbit");
  }

  @Test
  void shouldUpdateBook() {
    BookRequest request = new BookRequest("Old Title", authorId);
    ResponseEntity<BookDto> created =
        restTemplate.postForEntity("http://localhost:" + port + "/books", request, BookDto.class);

    BookRequest updateRequest = new BookRequest("New Title", authorId);
    ResponseEntity<BookDto> response =
        restTemplate.exchange(
            "http://localhost:" + port + "/books/" + created.getBody().id(),
            HttpMethod.PUT,
            new HttpEntity<>(updateRequest),
            BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().title()).isEqualTo("New Title");
  }

  @Test
  void shouldDeleteBook() {
    BookRequest request = new BookRequest("To Be Deleted", authorId);
    ResponseEntity<BookDto> created =
        restTemplate.postForEntity("http://localhost:" + port + "/books", request, BookDto.class);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            "http://localhost:" + port + "/books/" + created.getBody().id(),
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            Void.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
