package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class BookController {
  @Autowired private BookService bookService;

  @GetMapping("/books")
  public JSONObject searchBooks(String keyword, int pageIndex, int pageSize) {
    return bookService.searchBooks(keyword, pageIndex, pageSize);
  }

  @GetMapping("/book/{id}")
  public JSONObject getBookInfo(@PathVariable long id, @SessionAttribute("id") long userId) {
    return bookService.getBookInfo(id);
  }

  @GetMapping("/book/{id}/comments")
  public JSONObject getComments(
      @PathVariable long id,
      int pageIndex,
      int pageSize,
      String sort,
      @SessionAttribute("id") long userId) {
    return bookService.getBookComments(id, pageIndex, pageSize, sort, userId);
  }

  @PostMapping("/book/{id}/comments")
  public JSONObject postComment(
      @PathVariable long id, @RequestBody JSONObject body, @SessionAttribute("id") long userId) {
    return bookService.postComment(id, userId, body.getString("content"));
  }
}
