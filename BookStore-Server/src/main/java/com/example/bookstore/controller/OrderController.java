package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.*;
import com.example.bookstore.entity.Order;
import com.example.bookstore.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderController {
  @Autowired private OrderService orderService;

  @GetMapping
  public JSONArray getOrder(@SessionAttribute("id") long id) {
    JSONArray res = new JSONArray();
    for (Order order : orderService.getOrder(id)) res.add(orderService.toJson(order));
    return res;
  }

  @PostMapping
  public JSONObject placeOrder(@RequestBody JSONObject param, @SessionAttribute("id") long id) {
    JSONObject res = new JSONObject();
    JSONArray itemIds = param.getJSONArray("itemIds");
    List<Long> items = new ArrayList<>();
    for (int i = 0; i < itemIds.size(); ++i) items.add(itemIds.getLong(i));
    orderService.placeOrder(
        param.getString("address"), param.getString("receiver"), param.getString("tel"), items, id);
    res.put("ok", true);
    res.put("message", "下单成功!");
    return res;
  }
}
