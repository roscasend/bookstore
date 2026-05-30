package com.roscasend.web.bookstore.backend.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.roscasend.web.bookstore.backend.model.Author;
import com.roscasend.web.bookstore.backend.model.Book;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class BookMapperTest {

  @Test
  void toResponse_mapsAllScalarFields() {
    Book book = new Book("Clean Code", "Handbook", "cc.jpg", new BigDecimal("38.99"));
    // simulate persistence having assigned an id
    // (if Book.id has a setter — Lombok @Setter does this for you)
    book.setId(42L);

    BookResponse response = BookMapper.toResponse(book);

    assertThat(response.id()).isEqualTo(42L);
    assertThat(response.title()).isEqualTo("Clean Code");
    assertThat(response.description()).isEqualTo("Handbook");
    assertThat(response.imagePath()).isEqualTo("cc.jpg");
    assertThat(response.price()).isEqualByComparingTo("38.99");
    assertThat(response.authors()).isEmpty();
  }

  @Test
  void toResponse_mapsAuthors() {
    Book book = new Book("Pragmatic Programmer", "Classic", "pp.jpg", new BigDecimal("44.00"));
    book.setId(1L);

    Author bloch = new Author("Joshua", "Bloch");
    bloch.setId(10L);
    Author martin = new Author("Robert", "Martin");
    martin.setId(20L);

    book.getAuthors().add(bloch);
    book.getAuthors().add(martin);

    BookResponse response = BookMapper.toResponse(book);

    assertThat(response.authors())
        .hasSize(2)
        .extracting(BookResponse.AuthorSummary::firstName)
        .containsExactlyInAnyOrder("Joshua", "Robert");
  }
}