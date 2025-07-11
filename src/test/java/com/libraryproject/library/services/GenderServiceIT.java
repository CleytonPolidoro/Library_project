package com.libraryproject.library.services;

import com.libraryproject.library.entities.dto.GenderDTO;
import com.libraryproject.library.repositories.GenderRepository;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class GenderServiceIT {

    @Autowired
    private GenderService service;

    @Autowired
    private GenderRepository repository;

    private Long existingId, nonExistingId;
    private GenderDTO genderDTO, persistDto;
    private Pageable pageable;
    @BeforeEach
    void setUp() throws Exception{

        existingId = 1L;
        nonExistingId = 100L;
        genderDTO = Factory.createSaveGenderDto();
        persistDto = Factory.createGenderDTO();
        pageable = PageRequest.of(0, 10);
    }

    @Test
    public void findAllShouldReturnPage(){
        Page<GenderDTO> result = service.findAll(pageable, "");

        Assertions.assertTrue(result.hasContent());
    }

    @Test
    public void findByIdShouldReturnGenderWhenIdExists(){
        GenderDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldThrowsDatabaseExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void saveShouldInsertAndAutoIncrementWhenGenderIsNew(){
        GenderDTO result = service.save(genderDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), repository.count());
    }

    @Test
    public void saveShouldThrowsUnprocessableExceptionWhenGenderPersist(){
        Assertions.assertThrows(UnprocessableException.class, () -> {
           service.save(persistDto);
        });
    }
}
