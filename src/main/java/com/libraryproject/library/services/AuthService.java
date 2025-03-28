package com.libraryproject.library.services;

import com.libraryproject.library.entities.Role;
import com.libraryproject.library.entities.User;
import com.libraryproject.library.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(long userId){
        User me = userService.authenticated().get();
        if(!me.hasRole("SCOPE_ADMIN") && !me.getId().equals(userId)){
            throw new ForbiddenException("Access denied");
        }
    }
}
