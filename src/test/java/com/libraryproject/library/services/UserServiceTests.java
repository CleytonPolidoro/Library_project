package com.libraryproject.library.services;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.entities.dto.UserInsertDTO;
import com.libraryproject.library.entities.dto.UserUpdateDTO;
import com.libraryproject.library.entities.projections.UserDetailsProjection;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private String existingEmail;
    private String nonExistingEmail;
    private UserDetailsProjection userDetails;
    private User user;
    private Pageable pageable;
    private Page page;
    private UserDTO userDto;
    private UserInsertDTO insertDto;
    private UserUpdateDTO updateDto;

    @BeforeEach
    void setUp()throws Exception{
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        existingEmail = "cleyton@gmail.com";
        nonExistingEmail = "bob@gmail.com";
        userDetails = Factory.createUserDetailsDto();
        user = Factory.createUser();
        userDto = Factory.createUserDTO();
        page = new PageImpl<>(List.of(userDto));
        insertDto = Factory.createUserInsertDTO();
        updateDto = Factory.createUserUpdateDTO();

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(user));

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(user);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).getReferenceById(nonExistingId);

        Mockito.when(repository.searchUserAndRolesByEmail(existingEmail)).thenReturn(List.of(userDetails));
        Mockito.when(repository.searchAll(ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(repository.save(user)).thenReturn(user);
        Mockito.when(encoder.encode(insertDto.getPassword())).thenReturn(insertDto.getPassword().concat("649849ad"));
    }

    @Test
    public void findAllShouldReturnPage(){
        Page<UserDTO> result = service.findAll(ArgumentMatchers.any());

        Assertions.assertFalse(result.isEmpty());
        Mockito.verify(repository, Mockito.times(1)).searchAll(pageable);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExist(){
        UserDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

    }

    @Test
    public void insertShouldReturnUserDto(){
        UserDTO result = service.insert(insertDto);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).save(user);
    }

    @Test
    public void deletesShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(nonExistingId);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(() -> {
            service.deleteById(existingId);
        });
    }


    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId(){
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            service.deleteById(dependentId);
        });
    }

    @Test
    public void updateShouldReturnUserDtoWhenIdExists(){
        UserDTO result = service.update(existingId, updateDto);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).save(user);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, updateDto);
        });
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenIdExists(){
        UserDetails result = service.loadUserByUsername(existingEmail);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).searchUserAndRolesByEmail(existingEmail);
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
           service.loadUserByUsername(nonExistingEmail);
        });
    }


}
