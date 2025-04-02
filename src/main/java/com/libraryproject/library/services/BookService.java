package com.libraryproject.library.services;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.entities.dto.BookMinDTO;
import com.libraryproject.library.entities.dto.GenderDTO;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Transactional(readOnly = true)
    public Page<BookMinDTO> findAll(Pageable pageable, String title){
        Page<Book> result = repository.searchAll(pageable, title);
        return result.map(x -> new BookMinDTO(x));
    }

    @Transactional(readOnly = true)
    public BookDTO findById(Long id){
        Book result = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource not found. Id " + id));
        return new BookDTO(result);
    }

    @Transactional(readOnly = true)
    public Page<BookMinDTO> findByAuthor(Pageable pageable ,String author){

            Page<Book> result = repository.searchByAuthor(pageable, author);
            if(result.getContent().isEmpty()){
                throw new ResourceNotFoundException("Resource not found with author " + author);
            }
            return result.map(x -> new BookMinDTO(x));
    }


    @Transactional
    public BookDTO insert(BookDTO dto){

        boolean bookExists = repository.findAll().stream().anyMatch(g -> g.getTitle().equalsIgnoreCase(dto.getTitle()));

        if(bookExists){
            throw new UnprocessableException("Book already exists");
        }

        Book book = new Book();
        copyDtoToEntity(dto, book);

        Book result = repository.save(book);
        return new BookDTO(result);
    }

    private void copyDtoToEntity(BookDTO dto, Book entity) {
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setId(dto.getId());
        entity.setIsbn(dto.getIsbn());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPages(dto.getPages());

        entity.getGenders().clear();
        for(GenderDTO genDto : dto.getGenders()){
            Gender gender = new Gender();
            gender.setId(genDto.getId());
            gender.setName(genDto.getName());
            entity.addGender(gender);
        }
    }
}
