package com.libraryproject.library.services;

import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.repositories.GenderRepository;
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


}
