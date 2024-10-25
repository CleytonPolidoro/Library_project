package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByGendersNameContainingIgnoreCase(String gender);

    List<Book> findByTitleContainingIgnoreCase(String title);
}

