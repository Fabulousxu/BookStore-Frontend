package com.example.bookstore.entity.idclass;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeId implements Serializable {
  private long commentId;
  private long userId;
}
