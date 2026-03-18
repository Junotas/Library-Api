package com.ludvig.libraryapi.repository;

import com.ludvig.libraryapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
