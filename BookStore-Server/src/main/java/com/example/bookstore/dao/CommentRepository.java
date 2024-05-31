package com.example.bookstore.dao;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  Page<Comment> findByBookOrderByCreatedAtDesc(Book book, Pageable pageable);
}
