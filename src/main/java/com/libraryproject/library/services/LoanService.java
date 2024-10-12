package com.libraryproject.library.services;

import com.libraryproject.library.entities.Loan;
import com.libraryproject.library.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository repository;

    public List<Loan> findAll(){
        return repository.findAll();
    }

    public Loan findById(Long id){
        return repository.findById(id).get();
    }


}
