package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.BookRepository;
import com.example.bookstore.dao.UserRepository;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.CartItem;
import com.example.bookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {
  @Autowired private UserRepository userRepository;
  @Autowired private BookRepository bookRepository;

  @GetMapping
  public JSONArray getCart(@SessionAttribute("id") long id) {
    JSONArray res = new JSONArray();
    for (CartItem item : userRepository.findById(id).get().getCart()) res.add(item.toJson());
    return res;
  }

  @PutMapping
  public JSONObject addCart(long bookId, @SessionAttribute("id") long id) {
    JSONObject res = new JSONObject();
    Book book = bookRepository.findById(bookId).orElse(null);
    if (book == null) {
      res.put("ok", false);
      res.put("message", "书籍不存在");
    } else {
      User user = userRepository.findById(id).get();
      if (user.getCart().stream().anyMatch(item -> item.getBook().getBookId() == bookId)) {
        res.put("ok", false);
        res.put("message", "书籍已在购物车中");
      } else {
        user.getCart().add(new CartItem(user, book));
        userRepository.save(user);
        res.put("ok", true);
        res.put("message", "成功加入购物车");
      }
    }
    return res;
  }

  @PutMapping("/{id}")
  public JSONObject setNumber(
      @PathVariable long id, int number, @SessionAttribute("id") long userId) {
    JSONObject res = new JSONObject();
    if (number <= 0) {
      res.put("ok", false);
      res.put("message", "数量不合法");
    } else {
      User user = userRepository.findById(userId).get();
      CartItem item =
          user.getCart().stream().filter(it -> it.getCartItemId() == id).findFirst().orElse(null);
      if (item == null) {
        res.put("ok", false);
        res.put("message", "购物车项不存在");
      } else {
        item.setNumber(number);
        userRepository.save(user);
        res.put("ok", true);
        res.put("message", "成功修改数量");
      }
    }
    return res;
  }

  @DeleteMapping("/{id}")
  public JSONObject delCartItem(@PathVariable long id, @SessionAttribute("id") long userId) {
    JSONObject res = new JSONObject();
    User user = userRepository.findById(userId).get();
    if (user.getCart().removeIf(item -> item.getCartItemId() == id)) {
      userRepository.save(user);
      res.put("ok", true);
      res.put("message", "成功删除购物车项");
    } else {
      res.put("ok", false);
      res.put("message", "购物车项不存在");
    }
    return res;
  }
}
