package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

}
