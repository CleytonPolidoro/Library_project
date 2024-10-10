package com.libraryproject.library;

import com.libraryproject.library.entities.Client;
import com.libraryproject.library.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @GetMapping
    public ResponseEntity<User> findAll(){
        User client1 = new Client(1L,"Pedro Henrique","pedrohenrique@gmail.com", "12345678","999999999");
        return ResponseEntity.ok().body(client1);
    }


}
