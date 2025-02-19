package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.entities.dto.GenderDTO;
import com.libraryproject.library.services.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/genders")
public class GenderResource {

    @Autowired
    private GenderService service;

    @GetMapping
    public ResponseEntity<List<GenderDTO>> findAll(){
        List<GenderDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GenderDTO> findById(@PathVariable Long id){
        GenderDTO gender = service.findById(id);
        return ResponseEntity.ok().body(gender);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<GenderDTO> create(@RequestBody GenderDTO dto){
        service.save(dto);
        return ResponseEntity.ok().body(dto);
    }

}
