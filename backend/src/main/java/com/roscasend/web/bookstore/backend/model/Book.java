package com.roscasend.web.bookstore.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
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

}
