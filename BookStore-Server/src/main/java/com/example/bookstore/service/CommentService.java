package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
  JSONObject like(long commentId, long userId);

  JSONObject unlike(long commentId, long userId);
}
