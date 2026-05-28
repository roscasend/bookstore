package com.roscasend.web.bookstore.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;

  @ManyToMany
  @JoinTable(
      name = "book_author",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id")
  )
  private Set<Author> authors = new HashSet<>();

  private String description;
  private String imagePath;
  private BigDecimal price;

  protected Book() {
    // JPA requires a no-args constructor
  }

  public Book(String title, String description, String imagePath, BigDecimal price) {
    this.title = title;
    this.description = description;
    this.imagePath = imagePath;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(id, book.id) && Objects.equals(title, book.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title);
  }
}
