package com.libraryproject.library.services;

import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.entities.dto.GenderDTO;
import com.libraryproject.library.repositories.GenderRepository;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenderService {

    @Autowired
    private GenderRepository repository;

    @Transactional(readOnly = true)
    public List<GenderDTO> findAll(){
        List<Gender> genders = repository.findAll();
        return genders.stream().map(x -> new GenderDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public GenderDTO findById(Long id){
        Gender gender = repository.findById(id).get();
        return new GenderDTO(gender);
    }

    @Transactional(readOnly = true)
    public GenderDTO save(GenderDTO dto){
        boolean genderExists = repository.findAll().stream().anyMatch(g -> g.getName() == dto.getName());

        if(genderExists){
            throw new UnprocessableException("Gender already exists");
        }

        Gender gender = new Gender();
        copyDtoToEntity(dto, gender);

        Gender result = repository.save(gender);
        return new GenderDTO(result);
    }

    private void copyDtoToEntity(GenderDTO dto, Gender entity) {
        entity.setName(dto.getName());
        entity.setId(dto.getId());
    }
}

