package com.roscasend.web.bookstore.backend.configuration;

import com.roscasend.web.bookstore.backend.model.Book;
import com.roscasend.web.bookstore.backend.repository.BookRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(BookRepository repository) {
    return args -> {
      repository.save(new Book("1", "Book 1", "Description 1", new BigDecimal(10.12d)));
      repository.save(new Book("2", "Book 2", "Description 2",new BigDecimal(10.14d)));
      repository.save(new Book("3", "Book 3", "Description 3", new BigDecimal(10.17d)));
    };
  }
}
