package com.libraryproject.library.resources;

import com.libraryproject.library.entities.dto.GenderDTO;
import com.libraryproject.library.services.GenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<GenderDTO>> findAll(Pageable pageable,
                                                   @RequestParam(name = "gender", defaultValue = "") String gender){
        Page<GenderDTO> list = service.findAll(pageable, gender);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GenderDTO> findById(@PathVariable Long id){
        GenderDTO gender = service.findById(id);
        return ResponseEntity.ok().body(gender);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<GenderDTO> save(@RequestBody @Valid GenderDTO dto){
        GenderDTO result = service.save(dto);
        return ResponseEntity.ok().body(result);
    }

}
