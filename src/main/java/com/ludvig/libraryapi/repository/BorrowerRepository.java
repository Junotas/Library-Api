package com.ludvig.libraryapi.repository;

import com.ludvig.libraryapi.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {}
