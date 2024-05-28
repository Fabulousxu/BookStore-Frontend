package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
  JSONObject toJson(Comment comment, long userId);

  boolean addComment(long userId, long bookId, String content);

  boolean likeComment(long commentId, long userId);

  boolean unlikeComment(long commentId, long userId);
}
