package com.libraryproject.library.services;

import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.entities.dto.GenderDTO;
import com.libraryproject.library.repositories.GenderRepository;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class GenderServiceTests {

    @InjectMocks
    private GenderService service;

    @Mock
    private GenderRepository repository;

    GenderDTO genderDto;
    Gender gender;
    GenderDTO saveGenderDto;
    Gender saveGender;
    String existGenderName;
    String nonExistGenderName;
    Page page;
    Page pageEmpty;
    List list;
    List listEmpty;
    Pageable pageable;
    long existId;
    long nonExistingId;
    @BeforeEach
    void setUp() throws Exception{

        genderDto = Factory.createGenderDTO();
        gender = Factory.createGender();
        saveGenderDto = Factory.createSaveGenderDto();
        saveGender = Factory.createSaveGender();
        existGenderName = "Desenvolvimento pessoal";
        nonExistGenderName = "Romance";
        page = new PageImpl<>(List.of(gender));
        pageEmpty = new PageImpl<>(List.of());
        list = List.of(Factory.createGender());
        listEmpty = List.of();
        pageable = PageRequest.of(0, 10);
        existId = 1l;
        nonExistingId = 100l;


        Mockito.when(repository.findByNameContainingIgnoreCase(
                pageable, nonExistGenderName)).thenReturn(pageEmpty);
        Mockito.when(repository.searchAll(listEmpty)).thenReturn(listEmpty);

        Mockito.when(repository.findByNameContainingIgnoreCase(
                pageable, existGenderName)).thenReturn(page);
        Mockito.when(repository.searchAll(list)).thenReturn(list);

        Mockito.when(repository.findById(existId)).thenReturn(Optional.of(gender));
        Mockito.when(repository.save(saveGender)).thenReturn(saveGender);
        Mockito.when(repository.findAll()).thenReturn(list);
    }

    @Test
    public void findAllShouldReturnPageEmptyWhenGenderDoesNotExist(){
        Page<GenderDTO> result = service.findAll(pageable, nonExistGenderName);

        Assertions.assertTrue(result.isEmpty());

        Mockito.verify(repository).findByNameContainingIgnoreCase(pageable, nonExistGenderName);
        Mockito.verify(repository).searchAll(listEmpty);

    }

    @Test
    public void findAllShouldReturnPageWhenGenderExists(){
        Page<GenderDTO> result = service.findAll(pageable, existGenderName);

        Assertions.assertTrue(result.hasContent());

        Mockito.verify(repository).findByNameContainingIgnoreCase(pageable, existGenderName);
        Mockito.verify(repository).searchAll(list);
    }

    @Test
    public void findByIdShouldThrowsDatabaseExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnGenderDtoWhenIdExists(){
       GenderDTO result = service.findById(existId);

       Assertions.assertNotNull(result);
       Mockito.verify(repository).findById(existId);
    }

    @Test
    public void saveShouldReturnGenderDtoWhenGenderDoesNotExist(){
        GenderDTO result = service.save(saveGenderDto);

        Assertions.assertNotNull(result);
        Mockito.verify(repository).save(saveGender);
        Mockito.verify(repository).findAll();
    }

    @Test
    public void saveShouldThrowUnprocessableExceptionWhenGenderExist(){
        Assertions.assertThrows(UnprocessableException.class, () -> {
           service.save(genderDto);
           Mockito.verify(repository).findAll();
        });
    }

}
