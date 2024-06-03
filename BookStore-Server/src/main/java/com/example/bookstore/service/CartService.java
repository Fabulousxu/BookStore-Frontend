package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
  JSONArray getCart(long userId);

  JSONObject addCart(long bookId, long userId);

  JSONObject setNumber(long cartItemId, long userId, int number);

  JSONObject delCart(long cartItemId, long userId);
}
