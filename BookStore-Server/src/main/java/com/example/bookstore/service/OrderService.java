package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.OrderItem;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
  JSONObject toJson(OrderItem orderItem);

  JSONObject toJson(Order order);

  List<Order> getOrder(long userId);

  void placeOrder(String address, String receiver, String tel, List<Long> items, long userId);
}
