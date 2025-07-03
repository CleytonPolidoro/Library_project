package com.libraryproject.library.services;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.Role;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.repositories.RoleRepository;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> findAll(){
        return repository.findAll();
    }

    public Role findById(Long id){
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource not found. Id "+id));
    }

    public Role findByAuthority(String authority){
        try {
            return repository.findByAuthority(authority);

        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Resource not found. Authority "+ authority);
        }

    }
}
