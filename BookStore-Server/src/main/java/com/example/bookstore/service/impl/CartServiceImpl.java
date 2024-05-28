package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.CartItemRepository;
import com.example.bookstore.entity.CartItem;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
  @Autowired private CartItemRepository cartItemRepository;
  @Autowired private BookService bookService;

  public JSONObject toJson(CartItem cartItem) {
    JSONObject res = new JSONObject();
    res.put("id", cartItem.getCartItemId());
    res.put("book", bookService.toJson(cartItem.getBookId()));
    res.put("number", cartItem.getNumber());
    return res;
  }

  public List<CartItem> getCart(long userId) {
    return cartItemRepository.findByUserIdOrderByCartItemIdDesc(userId);
  }

  public boolean addCartItem(long userId, long bookId) {
    if (cartItemRepository.findByUserIdAndBookId(userId, bookId) != null) return false;
    CartItem cartItem = new CartItem();
    cartItem.setUserId(userId);
    cartItem.setBookId(bookId);
    cartItem.setNumber(1);
    cartItemRepository.save(cartItem);
    return true;
  }

  public boolean setNumber(long cartItemId, int number) {
    CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
    if (cartItem == null) return false;
    cartItem.setNumber(number);
    cartItemRepository.save(cartItem);
    return true;
  }

  public void delCartItem(long cartItemId) {
    cartItemRepository.deleteById(cartItemId);
  }
}
