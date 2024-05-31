package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.*;
import com.example.bookstore.entity.CartItem;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.OrderItem;
import com.example.bookstore.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderController {
  @Autowired private UserRepository userRepository;

  @GetMapping
  public JSONArray getOrder(@SessionAttribute("id") long id) {
    JSONArray res = new JSONArray();
    for (Order order : userRepository.findById(id).get().getOrders()) res.add(order.toJson());
    return res;
  }

  @PostMapping
  public JSONObject placeOrder(@RequestBody JSONObject param, @SessionAttribute("id") long id) {
    JSONObject res = new JSONObject();
    User user = userRepository.findById(id).get();
    JSONArray itemIds = param.getJSONArray("itemIds");
    List<Long> items = new ArrayList<>();
    for (int i = 0; i < itemIds.size(); i++) {
      int j = i;
      if (user.getCart().stream()
          .noneMatch(item -> item.getCartItemId() == itemIds.getLongValue(j))) {
        res.put("ok", false);
        res.put("message", "购物车中不存在该商品");
        return res;
      }
      items.add(itemIds.getLong(i));
    }
    Order order =
        new Order(
            user, param.getString("receiver"), param.getString("address"), param.getString("tel"));
    for (Long itemId : items) {
      CartItem item =
          user.getCart().stream().filter(it -> it.getCartItemId() == itemId).findFirst().get();
      order.getItems().add(new OrderItem(order, item.getBook(), item.getNumber()));
      user.getCart().remove(item);
    }
    user.getOrders().add(order);
    userRepository.save(user);
    res.put("ok", true);
    res.put("message", "下单成功!");
    return res;
  }
}
