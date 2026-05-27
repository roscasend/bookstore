package com.roscasend.web.bookstore.backend.controller;

import com.roscasend.web.bookstore.backend.exception.BookNotFoundException;
import com.roscasend.web.bookstore.backend.model.Book;
import com.roscasend.web.bookstore.backend.repository.BookRepository;
import java.util.List;
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

  BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @GetMapping()
  public List<Book> getBooks(){
      return bookRepository.findAll();
  }

  @GetMapping("/{id}")
  public Book getBook(@PathVariable Long id){
    return bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException(id));
  }


  @PostMapping()
  public Book saveBook(@RequestBody Book book){
      return bookRepository.save(book);
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
