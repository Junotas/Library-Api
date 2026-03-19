package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.AuthorDto;
import com.ludvig.libraryapi.dto.AuthorRequest;
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
class AuthorControllerTest {

  @Value("${server.port}")
  private int port;

  private final RestTemplate restTemplate = new RestTemplate();

  @Test
  void shouldReturnEmptyListWhenNoAuthors() {
    ResponseEntity<AuthorDto[]> response =
        restTemplate.getForEntity("http://localhost:" + port + "/authors", AuthorDto[].class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void shouldCreateAuthor() {
    AuthorRequest request = new AuthorRequest("J.K. Rowling");

    ResponseEntity<AuthorDto> response =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/authors", request, AuthorDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody().name()).isEqualTo("J.K. Rowling");
  }

  @Test
  void shouldGetAuthorById() {
    AuthorRequest request = new AuthorRequest("J.R.R. Tolkien");
    ResponseEntity<AuthorDto> created =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/authors", request, AuthorDto.class);

    ResponseEntity<AuthorDto> response =
        restTemplate.getForEntity(
            "http://localhost:" + port + "/authors/" + created.getBody().id(), AuthorDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().name()).isEqualTo("J.R.R. Tolkien");
  }

  @Test
  void shouldUpdateAuthor() {
    AuthorRequest request = new AuthorRequest("Old Name");
    ResponseEntity<AuthorDto> created =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/authors", request, AuthorDto.class);

    AuthorRequest updateRequest = new AuthorRequest("New Name");
    ResponseEntity<AuthorDto> response =
        restTemplate.exchange(
            "http://localhost:" + port + "/authors/" + created.getBody().id(),
            HttpMethod.PUT,
            new HttpEntity<>(updateRequest),
            AuthorDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().name()).isEqualTo("New Name");
  }

  @Test
  void shouldDeleteAuthor() {
    AuthorRequest request = new AuthorRequest("To Be Deleted");
    ResponseEntity<AuthorDto> created =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/authors", request, AuthorDto.class);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            "http://localhost:" + port + "/authors/" + created.getBody().id(),
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            Void.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
