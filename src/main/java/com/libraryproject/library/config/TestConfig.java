package com.libraryproject.library.config;

import com.libraryproject.library.entities.*;
import com.libraryproject.library.entities.enums.LoanStatus;
import com.libraryproject.library.repositories.BookRepository;
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

    @Autowired
    private BookRepository bookRepository;

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
        Gender gender2 = new Gender(null, "Desenvolvimento Pessoal");
        Gender gender3 = new Gender(null, "Science Fiction");

        Book book1 = new Book(null, "Dom Casmurro", "Machado de Assis", 208, 978_8594318602L, 24.90, "", gender1);
        Book book2 = new Book(null, "A arte da guerra", "Sun Tzu", 80, 978_6584956230L, 49.90, "", gender2);
        Book book3 = new Book(null, "Diário estoico", "Ryan Holiday", 496, 978_6555605556L, 64.90, "", gender2);
        Book book4 = new Book(null, "Box Duna: Primeira Trilogia", "Frank Herbert", 1480, 978_6586064414L, 299.90, "", gender3);
        Book book5 = new Book(null, "Middlemarch", "George Eliot", 880, 978_0141196893L, 149.90, "", gender1);


        userRepository.saveAll(Arrays.asList(client1, client2));
        loanRepository.saveAll(Arrays.asList(l1, l2, l3));
        genderRepository.saveAll(Arrays.asList(gender1, gender2, gender3));
        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5));
    }
}
