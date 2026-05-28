package com.roscasend.web.bookstore.backend.configuration;

import com.roscasend.web.bookstore.backend.model.Author;
import com.roscasend.web.bookstore.backend.model.Book;
import com.roscasend.web.bookstore.backend.repository.AuthorRepository;
import com.roscasend.web.bookstore.backend.repository.BookRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(BookRepository bookRepository, AuthorRepository authorRepository) {
    return args -> {
      if (bookRepository.count() == 0 && authorRepository.count() == 0) {
        Author bloch = authorRepository.save(new Author("Joshua", "Bloch"));
        Author martin = authorRepository.save(new Author("Robert", "Martin"));
        Author walls = authorRepository.save(new Author("Craig", "Walls"));

        Book effectiveJava = new Book(
            "Effective Java",
            "Best practices for Java developers",
            "effective-java.jpg",
            new BigDecimal("42.50")
        );
        effectiveJava.getAuthors().add(bloch);
        bookRepository.save(effectiveJava);
        Book cleanCode = new Book(
            "Clean Code",
            "A handbook of agile software craftsmanship",
            "clean-code.jpg",
            new BigDecimal("38.99")
        );
        cleanCode.getAuthors().add(martin);
        bookRepository.save(cleanCode);
        Book springInAction = new Book(
            "Spring in Action",
            "Cover-to-cover Spring Framework",
            "spring-in-action.jpg",
            new BigDecimal("49.95")
        );
        springInAction.getAuthors().add(walls);
        bookRepository.save(springInAction);
        Book pragmatic = new Book(
            "The Pragmatic Programmer",
            "From journeyman to master",
            "pragmatic.jpg",
            new BigDecimal("44.00")
        );
        pragmatic.getAuthors().add(bloch);
        pragmatic.getAuthors().add(martin);
        bookRepository.save(pragmatic);
      }
    };
  }
}
