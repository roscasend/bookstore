package com.roscasend.web.bookstore.backend.repository;

import com.roscasend.web.bookstore.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {

}

