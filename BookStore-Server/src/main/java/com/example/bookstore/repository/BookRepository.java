package com.example.bookstore.repository;

import com.example.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Page<Book> findByTitleContainsOrAuthorContainsOrDescriptionContainsOrIsbnContains(
      String title, String author, String description, String isbn, Pageable pageable);
}
