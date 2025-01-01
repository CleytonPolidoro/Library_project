package com.libraryproject.library.services;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public Optional<User> findByName(String name){
        return repository.findByUsername(name);
    }
    public User insert(User user){
        return repository.save(user);
    }

    public void deleteById(Long id){
        try {
            if(!repository.existsById(id)){
                throw new ResourceNotFoundException(id);
            }
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(Long id, User user){
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity, user);
            return repository.save(entity);
        } catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User user) {
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
    }

}
