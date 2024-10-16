package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.services.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/genders")
public class GenderResource {

    @Autowired
    private GenderService service;

    @GetMapping
    public ResponseEntity<List<Gender>> findAll(){
        List<Gender> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Gender> findById(@PathVariable Long id){
        Gender gender = service.findById(id);
        return ResponseEntity.ok().body(gender);
    }

}
