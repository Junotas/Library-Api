# Library API

A REST API for managing a library system. Built with Java and Spring Boot as part of a school examination project.

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Data JPA / Hibernate
- MySQL (via Docker)
- H2 (for testing)
- Spring Boot Validation
- Docker / Docker Compose

## Domain Model

Four entities with the following relationships:

- **Author** — writes many Books
- **Book** — belongs to one Author, can be loaned
- **Borrower** — can have many Loans
- **Loan** — connects a Borrower to a Book

## API Endpoints

| Method | Path | Request DTO | Response DTO |
|--------|------|-------------|--------------|
| `POST` | `/authors` | `AuthorRequest` | `AuthorDto` |
| `GET` | `/authors` | — | `List<AuthorDto>` |
| `GET` | `/authors/{id}` | — | `AuthorDto` |
| `PUT` | `/authors/{id}` | `AuthorRequest` | `AuthorDto` |
| `DELETE` | `/authors/{id}` | — | — |
| `POST` | `/books` | `BookRequest` | `BookDto` |
| `GET` | `/books` | — | `List<BookDto>` |
| `GET` | `/books/{id}` | — | `BookDto` |
| `PUT` | `/books/{id}` | `BookRequest` | `BookDto` |
| `DELETE` | `/books/{id}` | — | — |
| `POST` | `/borrowers` | `BorrowerRequest` | `BorrowerDto` |
| `GET` | `/borrowers` | — | `List<BorrowerDto>` |
| `GET` | `/borrowers/{id}` | — | `BorrowerDto` |
| `PUT` | `/borrowers/{id}` | `BorrowerRequest` | `BorrowerDto` |
| `DELETE` | `/borrowers/{id}` | — | — |
| `POST` | `/loans` | `LoanRequest` | `LoanDto` |
| `GET` | `/loans` | — | `List<LoanDto>` |
| `GET` | `/loans/{id}` | — | `LoanDto` |
| `PUT` | `/loans/{id}` | `LoanRequest` | `LoanDto` |
| `DELETE` | `/loans/{id}` | — | — |

## Architecture

Follows standard Spring Boot layered architecture:
```
controller  → handles HTTP requests and responses
dto         → data transfer objects (request/response)
service     → business logic and entity mapping
repository  → database access via Spring Data JPA
entity      → JPA entities / database tables
exception   → global exception handling
```

## Running the Application

Requires Docker. Start the full application with:
```bash
docker compose up --build
```

This starts both the MySQL database and the Spring Boot application.
API available at `http://localhost:8080`

## Running Tests

Tests use H2 in-memory database — no Docker required:
```bash
mvn test
```

20 integration tests covering full CRUD for all four entities.

## Building the JAR
```bash
mvn clean package -DskipTests
```
