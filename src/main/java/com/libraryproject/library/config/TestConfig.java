package com.libraryproject.library.config;

import com.libraryproject.library.entities.Client;
import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.entities.Loan;
import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.enums.LoanStatus;
import com.libraryproject.library.repositories.GenderRepository;
import com.libraryproject.library.repositories.LoanRepository;
import com.libraryproject.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Override
    public void run(String... args) throws Exception {
        User client1 = new Client(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        User client2 = new Client(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

        Loan l1 = new Loan(null, Instant.parse("2024-10-11T19:53:07Z"),
                Instant.parse("2024-10-18T18:00:00Z"), client1, LoanStatus.valueOf(2));

        Loan l2 = new Loan(null, Instant.parse("2024-10-09T03:42:10Z"),
                Instant.parse("2024-10-16T18:00:00Z"), client2, LoanStatus.valueOf(1));

        Loan l3 = new Loan(null, Instant.parse("2024-10-05T15:21:22Z"),
                Instant.parse("2024-10-12T18:00:00Z"), client1, LoanStatus.valueOf(1));

        Gender gender1 = new Gender(null, "Romance");
        Gender gender2 = new Gender(null, "Mystery");
        Gender gender3 = new Gender(null, "Science Fiction");
        Gender gender4 = new Gender(null, "Suspense e Terror");
        Gender gender5 = new Gender(null, "Desenvolvimento pessoal");

        userRepository.saveAll(Arrays.asList(client1, client2));
        loanRepository.saveAll(Arrays.asList(l1, l2, l3));
        genderRepository.saveAll(Arrays.asList(gender1, gender2, gender3, gender4, gender5));
    }
}
