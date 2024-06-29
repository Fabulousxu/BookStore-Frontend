package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
  JSONArray getOrderItems(long userId, String keyword);

  JSONObject placeOrder(
      List<Long> cartItemIds, long userId, String receiver, String address, String tel);

  JSONObject searchOrderItems(String keyword, int pageIndex, int pageSize);
}
