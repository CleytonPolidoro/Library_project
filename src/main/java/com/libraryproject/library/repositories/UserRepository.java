package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String admin);
    @Query("SELECT new com.libraryproject.library.entities.dto.UserDTO(obj.id, obj.username, obj.email, obj.phone) " +
            "FROM User obj")
    Page<UserDTO> searchAll(Pageable pageable);

    Optional<User> findByUsernameContainingIgnoreCase(String name);
}
