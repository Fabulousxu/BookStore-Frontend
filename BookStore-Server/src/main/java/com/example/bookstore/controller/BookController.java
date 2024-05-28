package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.BookRepository;
import com.example.bookstore.dao.CommentRepository;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Comment;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class BookController {
  @Autowired private BookService bookService;
  @Autowired private BookRepository bookRepository;
  @Autowired private CommentService commentService;
  @Autowired private CommentRepository commentRepository;

  @GetMapping("/books")
  public JSONObject getBooks(String keyword, int pageIndex, int pageSize) {
    JSONObject res = new JSONObject();
    Page<Book> bookPage =
        bookRepository.findByTitleContainsOrAuthorContainsOrDescriptionContains(
            keyword, keyword, keyword, PageRequest.of(pageIndex, pageSize));
    res.put("total", bookPage.getTotalPages());
    JSONArray items = new JSONArray();
    for (Book book : bookPage) items.add(bookService.toJson(book));
    res.put("items", items);
    return res;
  }

  @GetMapping("/book/{id}")
  public JSONObject getBook(@PathVariable long id) {
    Book book = bookRepository.findById(id).orElse(null);
    if (book != null) return bookService.toJson(book);
    else return new JSONObject();
  }

  @GetMapping("/book/{id}/comments")
  public JSONObject getComments(
      @PathVariable long id,
      int pageIndex,
      int pageSize,
      String sort,
      @SessionAttribute("id") long userId) {
    JSONObject res = new JSONObject();
    Page<Comment> commentPage =
        commentRepository.findByBookIdOrderByCommentIdDesc(id, PageRequest.of(pageIndex, pageSize));
    res.put("total", commentPage.getTotalPages());
    JSONArray items = new JSONArray();
    for (Comment comment : commentPage) items.add(commentService.toJson(comment, userId));
    res.put("items", items);
    return res;
  }

  @PostMapping("/book/{id}/comments")
  public JSONObject postComment(
      @PathVariable long id, @RequestBody JSONObject body, @SessionAttribute("id") long userId) {
    JSONObject res = new JSONObject();
    if (commentService.addComment(userId, id, body.getString("content"))) {
      res.put("ok", true);
      res.put("message", "评论成功");
    } else {
      res.put("ok", false);
      res.put("message", "书籍不存在");
    }
    return res;
  }
}
