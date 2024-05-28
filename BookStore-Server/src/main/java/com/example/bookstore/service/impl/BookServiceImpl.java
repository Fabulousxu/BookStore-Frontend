package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.BookRepository;
import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
  @Autowired private BookRepository bookRepository;

  public JSONObject toJson(Book book) {
    JSONObject json = new JSONObject();
    json.put("id", book.getBookId());
    json.put("title", book.getTitle());
    json.put("author", book.getAuthor());
    json.put("description", book.getDescription());
    json.put("price", book.getPrice());
    json.put("cover", book.getCover());
    json.put("sales", book.getSales());
    return json;
  }

  public JSONObject toJson(long bookId) {
    Book book = bookRepository.findById(bookId).orElse(null);
    return book == null ? null : toJson(book);
  }
}
