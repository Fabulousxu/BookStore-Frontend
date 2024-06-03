package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {
  @Autowired private CartService cartService;

  @GetMapping
  public JSONArray getCart(@SessionAttribute("id") long id) {
    return cartService.getCart(id);
  }

  @PutMapping
  public JSONObject addCart(long bookId, @SessionAttribute("id") long id) {
    return cartService.addCart(bookId, id);
  }

  @PutMapping("/{id}")
  public JSONObject setNumber(
      @PathVariable long id, int number, @SessionAttribute("id") long userId) {
    return cartService.setNumber(id, userId, number);
  }

  @DeleteMapping("/{id}")
  public JSONObject delCartItem(@PathVariable long id, @SessionAttribute("id") long userId) {
    return cartService.delCart(id, userId);
  }
}
