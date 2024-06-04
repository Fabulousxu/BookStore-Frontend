package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manage")
@CrossOrigin
public class ManageController {
  @Autowired private UserService userService;
  @Autowired private BookService bookService;
  @Autowired private OrderService orderService;

  @GetMapping("/users")
  public JSONObject searchUsers(
      String keyword, int pageIndex, int pageSize, @SessionAttribute("id") long id) {
    return userService.searchUsers(keyword, pageIndex, pageSize);
  }

  @GetMapping("/orders")
  public JSONObject searchOrderItems(
      String keyword, int pageIndex, int pageSize, @SessionAttribute("id") long id) {
    return orderService.searchOrderItems(keyword, pageIndex, pageSize);
  }

  @PostMapping("/book/{id}")
  public JSONObject setBook(
      @PathVariable long id, @RequestBody JSONObject param, @SessionAttribute("id") long userId) {
    return bookService.setBookInfo(
        id,
        param.getString("title"),
        param.getString("author"),
        param.getString("isbn"),
        param.getString("cover"),
        param.getString("description"),
        param.getIntValue("price"),
        param.getIntValue("sales"),
        param.getIntValue("repertory"));
  }

  @PostMapping("/user/{id}")
  public JSONObject setUser(
      @PathVariable long id, @RequestBody JSONObject param, @SessionAttribute("id") long userId) {
    return userService.setUserInfo(id, param.getString("username"), param.getString("email"));
  }

  @PutMapping("/book")
  public JSONObject addBook(@RequestBody JSONObject param, @SessionAttribute("id") long userId) {
    return bookService.addBook(
        param.getString("title"),
        param.getString("author"),
        param.getString("isbn"),
        param.getString("cover"),
        param.getString("description"),
        param.getIntValue("price"),
        param.getIntValue("sales"),
        param.getIntValue("repertory"));
  }

  @DeleteMapping("/book/{id}")
  public JSONObject deleteBook(@PathVariable long id, @SessionAttribute("id") long userId) {
    return bookService.deleteBook(id);
  }

  @PostMapping("/user/{id}/silence")
  public JSONObject silenceUser(@PathVariable long id, @SessionAttribute("id") long userId) {
    return userService.silenceUser(id);
  }

  @PostMapping("/user/{id}/unsilence")
  public JSONObject unsilenceUser(@PathVariable long id, @SessionAttribute("id") long userId) {
    return userService.unsilenceUser(id);
  }
}
