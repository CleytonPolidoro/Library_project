package com.libraryproject.library.services;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.entities.dto.UserInsertDTO;
import com.libraryproject.library.entities.dto.UserUpdateDTO;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable){
        Page<UserDTO> users = repository.searchAll(pageable);
        return users;
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        User user = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource not found. Id "+id));
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUsernameForToken(String username){
        return repository.findByEmail(username);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto){
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UserDTO(entity);
    }
    @Transactional(readOnly = true)
    public void deleteById(Long id){
        try {
            if(!repository.existsById(id)){
                throw new ResourceNotFoundException("Resource not found. Id "+ id);
            }
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public UserDTO update(Long id, UserUpdateDTO dto){
        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Resource not found. Id "+id);
        }
    }

    protected Optional<User> authenticated(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt principal = (Jwt) authentication.getPrincipal();
            String username = principal.getClaim("username");
            return repository.findByEmail(username);
        }catch(Exception e){
            throw new ResourceNotFoundException("Email not found");
        }
    }
    @Transactional(readOnly = true)
    public UserDTO getMe(){
        User user = authenticated().get();
        return new UserDTO(user);
    }

    public void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());

    }
}
