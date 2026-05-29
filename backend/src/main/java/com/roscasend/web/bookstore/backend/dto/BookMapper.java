package com.roscasend.web.bookstore.backend.dto;

import com.roscasend.web.bookstore.backend.dto.BookResponse.AuthorSummary;
import com.roscasend.web.bookstore.backend.model.Book;
import java.util.List;

public class BookMapper {

  public static BookResponse toResponse(Book book) {
    List<AuthorSummary> authors = book.getAuthors().stream()
        .map(a -> new BookResponse.AuthorSummary(a.getId(), a.getFirstName(), a.getLastName()))
        .toList();
    return new BookResponse(
        book.getId(),
        book.getTitle(),
        book.getDescription(),
        book.getImagePath(),
        book.getPrice(),
        authors
    );
  }
}
