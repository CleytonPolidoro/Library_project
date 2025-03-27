package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Role;
import com.libraryproject.library.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleResource {

    @Autowired
    private RoleService service;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Role>> findAll(){
        List<Role> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Role> findById(@PathVariable Long id){
        Role role = service.findById(id);
        return ResponseEntity.ok().body(role);
    }

}
