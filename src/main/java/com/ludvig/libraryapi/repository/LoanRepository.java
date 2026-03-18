package com.ludvig.libraryapi.repository;

import com.ludvig.libraryapi.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {}
