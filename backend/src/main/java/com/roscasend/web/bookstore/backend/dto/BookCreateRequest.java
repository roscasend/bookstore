package com.roscasend.web.bookstore.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record BookCreateRequest(
    @NotBlank(message = "title must not be blank")
     String title,

    @NotBlank(message = "description must not be blank")
    String description,
    String imagePath,
    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
    BigDecimal price,
    List<Long> authorIds
) {}