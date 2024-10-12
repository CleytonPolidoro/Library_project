package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanRepository extends JpaRepository<Loan, Long> {

}
