package com.libraryproject.library.services;

import com.libraryproject.library.entities.Role;
import com.libraryproject.library.repositories.RoleRepository;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class RoleServiceIT {

    @Autowired
    private RoleService service;

    @Autowired
    private RoleRepository repository;

    private Long existingId, nonExistingId;
    private String existingAuthority, nonExistingAuthority;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        existingAuthority = "SCOPE_ADMIN";
        nonExistingAuthority = "OPERÁRIO";
    }

    @Test
    public void findAllShouldReturnList(){
        List<Role> result = service.findAll();

        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findByIdShouldReturnRoleWhenIdExists(){
        Role result = service.findById(existingId);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void findByAuthorityShouldReturnRoleWhenAuthorityExists(){
        Role result = service.findByAuthority(existingAuthority);

        Assertions.assertNotNull(result);
    }

}
