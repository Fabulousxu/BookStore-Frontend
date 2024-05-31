package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.BookRepository;
import com.example.bookstore.dao.CommentRepository;
import com.example.bookstore.dao.UserRepository;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class BookController {
  @Autowired private UserRepository userRepository;
  @Autowired private BookRepository bookRepository;
  @Autowired private CommentRepository commentRepository;

  @GetMapping("/books")
  public JSONObject getBooks(String keyword, int pageIndex, int pageSize) {
    JSONObject res = new JSONObject();
    Page<Book> bookPage =
        bookRepository.findByTitleContainsOrAuthorContainsOrDescriptionContains(
            keyword, keyword, keyword, PageRequest.of(pageIndex, pageSize));
    res.put("total", bookPage.getTotalPages());
    JSONArray items = new JSONArray();
    for (Book book : bookPage) items.add(book.toJson());
    res.put("items", items);
    return res;
  }

  @GetMapping("/book/{id}")
  public JSONObject getBook(@PathVariable long id) {
    return bookRepository.findById(id).get().toJson();
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
        commentRepository.findByBookOrderByCreatedAtDesc(
            bookRepository.findById(id).get(), PageRequest.of(pageIndex, pageSize));
    res.put("total", commentPage.getTotalPages());
    JSONArray items = new JSONArray();
    for (Comment comment : commentPage)
      items.add(comment.toJson(userRepository.findById(userId).get()));
    res.put("items", items);
    return res;
  }

  @PostMapping("/book/{id}/comments")
  public JSONObject postComment(
      @PathVariable long id, @RequestBody JSONObject param, @SessionAttribute("id") long userId) {
    JSONObject res = new JSONObject();
    Book book = bookRepository.findById(id).orElse(null);
    if (book == null) {
      res.put("ok", false);
      res.put("message", "书籍不存在");
    } else {
      book.getComments()
          .add(
              new Comment(userRepository.findById(userId).get(), book, param.getString("content")));
      bookRepository.save(book);
      res.put("ok", true);
      res.put("message", "评论成功");
    }
    return res;
  }
}
