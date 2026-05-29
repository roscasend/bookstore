package com.roscasend.web.bookstore.backend.controller;

import com.roscasend.web.bookstore.backend.dto.BookCreateRequest;
import com.roscasend.web.bookstore.backend.dto.BookMapper;
import com.roscasend.web.bookstore.backend.dto.BookResponse;
import com.roscasend.web.bookstore.backend.exception.BookNotFoundException;
import com.roscasend.web.bookstore.backend.exception.InvalidAuthorIdsException;
import com.roscasend.web.bookstore.backend.model.Author;
import com.roscasend.web.bookstore.backend.model.Book;
import com.roscasend.web.bookstore.backend.repository.AuthorRepository;
import com.roscasend.web.bookstore.backend.repository.BookRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  @GetMapping()
  public List<BookResponse> getBooks(){
      return bookRepository.findAll().stream().map(BookMapper::toResponse).toList();
  }

  @GetMapping("/{id}")
  public BookResponse getBook(@PathVariable Long id){
    return BookMapper.toResponse(bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException(id)));
  }

  @PostMapping
  public BookResponse createBook(@Valid @RequestBody BookCreateRequest request) {
    List<Long> authorIds = request.authorIds() != null ? request.authorIds() : List.of();

    List<Author> authors = authorRepository.findAllById(authorIds);
    if (authors.size() != authorIds.size()) {
      Set<Long> foundIds = authors.stream().map(Author::getId).collect(Collectors.toSet());
      List<Long> missing = authorIds.stream()
          .filter(id -> !foundIds.contains(id))
          .toList();
      throw new InvalidAuthorIdsException(missing);
    }

    Book book = new Book(
        request.title(),
        request.description(),
        request.imagePath(),
        request.price()
    );
    book.getAuthors().addAll(authors);

    Book saved = bookRepository.save(book);
    return BookMapper.toResponse(saved);
  }

  @PutMapping("/{id}")
  Book replaceBook(@RequestBody Book book, @PathVariable Long id) {
    return bookRepository.findById(id)
    .map( existing -> {
      existing.setTitle(book.getTitle());
      existing.setDescription(book.getDescription());
      existing.setImagePath(book.getImagePath());
      existing.setPrice(book.getPrice());
      return bookRepository.save(existing);
    })
    .orElseThrow(() -> new BookNotFoundException(id));
  }

  @DeleteMapping("/{id}")
  void deleteBook(@PathVariable Long id) {
    bookRepository.deleteById(id);
  }
}
