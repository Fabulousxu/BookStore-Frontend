package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
  JSONArray getOrder(long userId);

  JSONArray searchOrderItems(long userId, String keyword, int pageIndex, int pageSize);

  JSONObject placeOrder(
      List<Long> cartItemIds, long userId, String receiver, String address, String tel);

  JSONObject searchOrderItems(String keyword, int pageIndex, int pageSize);
}
