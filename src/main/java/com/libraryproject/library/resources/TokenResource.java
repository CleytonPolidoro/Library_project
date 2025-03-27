package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Role;
import com.libraryproject.library.entities.dto.LoginRequestDTO;
import com.libraryproject.library.entities.dto.LoginResponseDTO;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TokenResource {

    private final JwtEncoder jwtEncoder;

    private final UserService service;


    private BCryptPasswordEncoder passwordEncoder;

    public TokenResource(JwtEncoder jwtEncoder, UserService service, UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        var user = service.findUsernameForToken(loginRequestDTO.email());

        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequestDTO, passwordEncoder)){
          throw new BadCredentialsException("Invalid username or password!");
        }

        var now = Instant.now();
        var expiresIn = 300L;
        List<String> authorities = user.get().getRoles().stream().
                map(Role::getAuthority).toList();

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", authorities)
                .claim("username", user.get().getUsername())
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }
}
