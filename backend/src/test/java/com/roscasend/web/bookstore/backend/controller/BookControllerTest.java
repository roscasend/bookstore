package com.roscasend.web.bookstore.backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.roscasend.web.bookstore.backend.exception.GlobalExceptionHandler;
import com.roscasend.web.bookstore.backend.model.Author;
import com.roscasend.web.bookstore.backend.model.Book;
import com.roscasend.web.bookstore.backend.repository.AuthorRepository;
import com.roscasend.web.bookstore.backend.repository.BookRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest(controllers = BookController.class)
@Import(GlobalExceptionHandler.class)
class BookControllerTest {

  @Autowired
  private org.springframework.test.web.servlet.MockMvc mockMvc;

  @MockitoBean
  private BookRepository bookRepository;

  @MockitoBean
  private AuthorRepository authorRepository;

  @Test
  void getBook_returns200AndJson() throws Exception {
    Author bloch = new Author("Joshua", "Bloch");
    bloch.setId(1L);
    Book book = new Book("Effective Java", "Desc", "ej.jpg", new BigDecimal("42.50"));
    book.setId(10L);
    book.getAuthors().add(bloch);

    when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

    mockMvc.perform(get("/api/books/10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.title").value("Effective Java"))
        .andExpect(jsonPath("$.authors[0].firstName").value("Joshua"));
  }

  @Test
  void getBook_unknownId_returns404() throws Exception {
    when(bookRepository.findById(99999L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/books/99999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Book not found with id: 99999"));
  }

  @Test
  void createBook_invalidBody_returns400() throws Exception {
    mockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"title":"","description":"","price":-1}
                    """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.title").exists());
  }
}