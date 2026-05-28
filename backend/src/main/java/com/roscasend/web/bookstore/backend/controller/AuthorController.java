package com.roscasend.web.bookstore.backend.controller;

import com.roscasend.web.bookstore.backend.exception.AuthorNotFoundException;
import com.roscasend.web.bookstore.backend.model.Author;
import com.roscasend.web.bookstore.backend.repository.AuthorRepository;
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
@RequestMapping("/api/authors")
public class AuthorController {

  private final AuthorRepository authorRepository;

  AuthorController(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @GetMapping()
  public List<Author> getAuthors(){
    return authorRepository.findAll();
  }

  @GetMapping("/{id}")
  public Author getAuthor(@PathVariable Long id){
    return authorRepository.findById(id)
        .orElseThrow(() -> new AuthorNotFoundException(id));
  }

  @PostMapping()
  public Author saveAuthor(@RequestBody Author author){
    return authorRepository.save(author);
  }

  @PutMapping("/{id}")
  Author replaceAuthor(@RequestBody Author author, @PathVariable Long id) {
    return authorRepository.findById(id)
        .map(existing -> {
          existing.setFirstName(author.getFirstName());
          existing.setLastName(author.getLastName());
          return authorRepository.save(existing);
        })
        .orElseThrow(() -> new AuthorNotFoundException(id));
  }

  @DeleteMapping("/{id}")
  void deleteAuthor(@PathVariable Long id) {
    authorRepository.deleteById(id);
  }
}
