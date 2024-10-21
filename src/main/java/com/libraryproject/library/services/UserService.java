package com.libraryproject.library.services;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        return repository.findById(id).get();
    }

    public User insert(User user){
        return repository.save(user);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

}
