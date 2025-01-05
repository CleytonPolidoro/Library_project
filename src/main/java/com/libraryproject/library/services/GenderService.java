package com.libraryproject.library.services;

import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.repositories.GenderRepository;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {

    @Autowired
    private GenderRepository repository;

    public List<Gender> findAll(){
        return repository.findAll();
    }

    public Gender findById(Long id){
        return repository.findById(id).get();
    }

    public Gender save(Gender gender){
        boolean genderExists = repository.findAll().stream().anyMatch(g -> g.getName() == gender.getName());

        if(genderExists){
            throw new UnprocessableException("Gender already exists");
        }

        return repository.save(gender);
    }
}
