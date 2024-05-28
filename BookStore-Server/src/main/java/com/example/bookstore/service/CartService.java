package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.CartItem;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
  JSONObject toJson(CartItem cartItem);

  List<CartItem> getCart(long userId);

  boolean addCartItem(long userId, long bookId);

  boolean setNumber(long cartItemId, int number);

  void delCartItem(long cartItemId);
}
