package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
  JSONObject toJson(Book book);

  JSONObject toJson(long bookId);
}
