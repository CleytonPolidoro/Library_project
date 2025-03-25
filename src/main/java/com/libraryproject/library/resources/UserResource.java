package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Role;
import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.CreateUserDTO;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.services.RoleService;
import com.libraryproject.library.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();


    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
        Page<UserDTO> page = service.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }


    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }


    @PostMapping()
    public ResponseEntity<UserDTO> save(@RequestBody @Valid CreateUserDTO dto){


        User user = new User();

        Role basicRole = roleService.findByAuthority(Role.Values.BASIC.name());
        Set<Role> roles = new HashSet<>();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        roles.add(basicRole);
        user.setRoles(roles);
        user.setPassword(bCrypt.encode(dto.password()));

        UserDTO userDto = new UserDTO(user);

        service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDto.getId()).toUri();


        return ResponseEntity.created(uri).body(userDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

}
