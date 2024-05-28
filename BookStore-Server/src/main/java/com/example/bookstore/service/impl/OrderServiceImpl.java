package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.CartItemRepository;
import com.example.bookstore.dao.OrderItemRepository;
import com.example.bookstore.dao.OrderRepository;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.OrderItem;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  @Autowired private BookService bookService;
  @Autowired private OrderRepository orderRepository;
  @Autowired private OrderItemRepository orderItemRepository;
  @Autowired private CartItemRepository cartItemRepository;

  public JSONObject toJson(OrderItem orderItem) {
    JSONObject json = new JSONObject();
    json.put("id", orderItem.getOrderItemId());
    json.put("book", bookService.toJson(orderItem.getBookId()));
    json.put("number", orderItem.getNumber());
    return json;
  }

  public JSONObject toJson(Order order) {
    JSONObject json = new JSONObject();
    json.put("id", order.getOrderId());
    json.put("receiver", order.getReceiver());
    json.put("address", order.getAddress());
    json.put("tel", order.getTel());
    json.put("createdAt", order.getCreatedAt());
    JSONArray items = new JSONArray();
    for (OrderItem orderItem : orderItemRepository.findByOrderId(order.getOrderId()))
      items.add(toJson(orderItem));
    json.put("items", items);
    return json;
  }

  public List<Order> getOrder(long userId) {
    return orderRepository.findByUserIdOrderByOrderIdDesc(userId);
  }

  public void placeOrder(
      String address, String receiver, String tel, List<Long> items, long userId) {
    Order order = new Order();
    order.setUserId(userId);
    order.setAddress(address);
    order.setReceiver(receiver);
    order.setTel(tel);
    orderRepository.save(order);
    for (long itemId : items)
      cartItemRepository
          .findById(itemId)
          .ifPresent(
              cartItem -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(order.getOrderId());
                orderItem.setBookId(cartItem.getBookId());
                orderItem.setNumber(cartItem.getNumber());
                orderItemRepository.save(orderItem);
                cartItemRepository.delete(cartItem);
              });
  }
}
