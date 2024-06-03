package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
  JSONObject searchBooks(String keyword, int pageIndex, int pageSize);

  JSONObject getBookInfo(long bookId);

  JSONObject getBookComments(long bookId, int pageIndex, int pageSize, String sort, long userId);

  JSONObject postComment(long bookId, long userId, String content);

  JSONObject setBookInfo(
      long bookId,
      String title,
      String author,
      String isbn,
      String cover,
      String description,
      int price,
      int sales,
      int repertory);

  JSONObject addBook(
      String title,
      String author,
      String isbn,
      String cover,
      String description,
      int price,
      int sales,
      int repertory);

  JSONObject deleteBook(long bookId);
}
