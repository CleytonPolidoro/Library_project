package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT obj FROM Book obj " +
            "WHERE UPPER(obj.title) LIKE UPPER(CONCAT('%', :title, '%') ) " +
            "AND UPPER(obj.author) LIKE UPPER(CONCAT('%', :author, '%') )"
    )
    Page<Book>searchAll(Pageable pageable, String title, String author);

    @Query("SELECT obj FROM Book obj " +
            "WHERE UPPER(obj.author) LIKE UPPER(CONCAT('%', :author, '%'))")
    Page<Book>searchByAuthor(Pageable pageable, String author);


    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByGendersNameContainingIgnoreCase(String gender);

    List<Book> findByTitleContainingIgnoreCase(String title);
}

