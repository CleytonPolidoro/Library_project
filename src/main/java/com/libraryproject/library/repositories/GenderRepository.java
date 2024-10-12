package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {

}
