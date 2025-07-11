package com.libraryproject.library.services;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.entities.dto.UserInsertDTO;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@SpringBootTest
@Transactional
public class UserServiceIT {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    Pageable pageable;
    Long existingId, nonExistingId, dependentId;
    UserInsertDTO userInsert;
    User user;
    String nonExistingEmail;

    @BeforeEach
    void setUp() throws Exception{
        pageable = PageRequest.of(0, 10);
        existingId = 1L;
        nonExistingId = 100L;
        userInsert = Factory.createUserInsertDTO();
        dependentId = 3L;
        user = Factory.createUser();
        nonExistingEmail = "jamida@gmail.com";

//        Mockito.when(repository.findByEmail(any())).thenReturn(Optional.of(user));
//        Mockito.doThrow(ResourceNotFoundException.class).when(repository).findByEmail(eq(nonExistingEmail));
    }

    @Test
    public void findAllShouldReturnPage(){
        Page<UserDTO> result = service.findAll(any());

        Assertions.assertTrue(result.hasContent());
    }

    @Test
    public void findByIdShouldReturnUserDtoWhenIdExists(){

        UserDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);

    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

    }

    @Test
    public void insertShouldSaveObjectAndAutoIncrement(){
        UserDTO result = service.insert(userInsert);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
    }

    @Test
    public void deleteShouldDeleteWhenIdExists(){
        service.deleteById(existingId);
        Assertions.assertFalse(repository.existsById(existingId));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(nonExistingId);
        });
    }

    @Test
    public void authenticatedShouldThrowResourceNotFoundExceptionWhenEmailDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.authenticated();
        });
    }

}
