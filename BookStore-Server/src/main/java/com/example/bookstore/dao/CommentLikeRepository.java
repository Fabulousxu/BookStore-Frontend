package com.example.bookstore.dao;

import com.example.bookstore.entity.CommentLike;
import com.example.bookstore.entity.idclass.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {}
