package com.roscasend.web.bookstore.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.roscasend.web.bookstore.backend.model.Author;
import com.roscasend.web.bookstore.backend.model.Book;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @Test
  void saveBookWithTwoAuthors_persistsJoinTable() {
    Author bloch = authorRepository.save(new Author("Joshua", "Bloch"));
    Author martin = authorRepository.save(new Author("Robert", "Martin"));

    Book book = new Book("Pragmatic Programmer", "Classic", "pp.jpg", new BigDecimal("44.00"));
    book.getAuthors().add(bloch);
    book.getAuthors().add(martin);

    Book saved = bookRepository.save(book);

    assertThat(saved.getId()).isNotNull();

    Book reloaded = bookRepository.findById(saved.getId()).orElseThrow();
    assertThat(reloaded.getAuthors())
        .hasSize(2)
        .extracting(Author::getLastName)
        .containsExactlyInAnyOrder("Bloch", "Martin");
  }

  @Test
  void findAll_returnsSeededBooks_whenYouSaveManually() {
    bookRepository.save(new Book("Effective Java", "Desc", "ej.jpg", new BigDecimal("42.50")));

    assertThat(bookRepository.findAll()).hasSize(1);
  }
}