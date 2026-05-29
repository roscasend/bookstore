package com.roscasend.web.bookstore.backend.dto;

import java.math.BigDecimal;
import java.util.List;

public record BookResponse(
    Long id,
    String title,
    String description,
    String imagePath,
    BigDecimal price,
    List<AuthorSummary> authors
) {
  public record AuthorSummary(Long id, String firstName, String lastName) {}
}
