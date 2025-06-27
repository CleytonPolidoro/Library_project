package com.libraryproject.library.services;

import com.libraryproject.library.entities.Role;
import com.libraryproject.library.repositories.RoleRepository;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class RoleServiceTests {

    @InjectMocks
    RoleService service;

    @Mock
    RoleRepository repository;

    Role role;
    Long existingId;
    String authority;

    @BeforeEach
    void setUp() throws Exception{
        role = Factory.createRole();
        existingId = 1L;
        authority = Role.Values.ADMIN.name();

        Mockito.when(repository.findAll()).thenReturn(List.of(role));
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(role));
        Mockito.when(repository.findByAuthority(authority)).thenReturn(role);
    }

    @Test
    public void findAllShouldReturnListOfRole(){
        List<Role> result = service.findAll();

        Assertions.assertFalse(result.isEmpty());
        Mockito.verify(repository).findAll();
    }

    @Test
    public void findByIdShouldReturnRoleWhenIdExists(){
        Role result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Mockito.verify(repository).findById(existingId);
    }

    @Test
    public void findByAuthorityWhenAuthorityExists(){
        Role result = service.findByAuthority(authority);

        Assertions.assertNotNull(result);
        Mockito.verify(repository).findByAuthority(authority);
    }

}
