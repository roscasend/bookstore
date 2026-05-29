package com.roscasend.web.bookstore.backend.exception;

public class InvalidAuthorIdsException extends RuntimeException {
  public InvalidAuthorIdsException(java.util.List<Long> missingIds) {
    super("Unknown author id(s): " + missingIds);
  }
}
