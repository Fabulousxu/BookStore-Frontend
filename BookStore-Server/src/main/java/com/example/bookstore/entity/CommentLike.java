package com.example.bookstore.entity;

import com.example.bookstore.entity.idclass.CommentLikeId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CommentLikeId.class)
public class CommentLike {
  @Id private long commentId;
  @Id private long userId;
}
