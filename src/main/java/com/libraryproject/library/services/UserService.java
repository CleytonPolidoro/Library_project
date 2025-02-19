package com.libraryproject.library.services;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll(){
        List<User> users = repository.findAll();

        return users.stream().map(x -> new UserDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        User user = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDTO findByName(String name){
        Optional<User> user = repository.findByUsername(name);
        return new UserDTO(user.get());
    }

    @Transactional(readOnly = true)
    public Optional<User> findUsernameForToken(String username){
        return repository.findByUsername(username);
    }

    @Transactional
    public UserDTO insert(UserDTO dto){
        User user = new User();
        copyDtoToEntity(dto, user);
        User result = repository.save(user);
        return new UserDTO(result);
    }
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public UserDTO update(Long id, UserDTO dto){
        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setUsername(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setId(dto.getId());

    }
}
