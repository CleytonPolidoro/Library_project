package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenderRepository extends JpaRepository<Gender, Long> {

    @Query("SELECT obj FROM Gender obj JOIN FETCH obj.book "+
            "WHERE obj in :genders")
    List<Gender> searchAll(List<Gender> genders);

    Page<Gender> findByNameContainingIgnoreCase(Pageable pageable, String name);

    Gender findByNameContainingIgnoreCase(String name);
}
