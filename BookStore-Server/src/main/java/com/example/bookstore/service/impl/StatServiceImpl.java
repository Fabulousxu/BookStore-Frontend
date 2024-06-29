package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.OrderItem;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.StatService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl implements StatService {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  @Autowired private OrderItemRepository orderItemRepository;
  @Autowired private UserRepository userRepository;

  @Override
  public JSONArray getBookStat(String timeBegin, String timeEnd, int number) {
    List<OrderItem> orderItems =
        orderItemRepository.findByOrder_CreatedAtBetween(
            LocalDateTime.parse(timeBegin, formatter), LocalDateTime.parse(timeEnd, formatter));
    Map<Book, Integer> bookSales = new HashMap<>();
    for (OrderItem orderItem : orderItems) {
      Book book = orderItem.getBook();
      bookSales.put(book, bookSales.getOrDefault(book, 0) + orderItem.getNumber());
    }
    List<Map.Entry<Book, Integer>> sortedBookSales =
        bookSales.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(number)
            .toList();
    JSONArray bookStatArray = new JSONArray();
    for (Map.Entry<Book, Integer> entry : sortedBookSales) {
      JSONObject bookStat = entry.getKey().toJson();
      bookStat.put("sales", entry.getValue());
      bookStatArray.add(bookStat);
    }
    return bookStatArray;
  }

  @Override
  public JSONArray getUserStat(String timeBegin, String timeEnd, int number) {
    List<OrderItem> orderItems =
        orderItemRepository.findByOrder_CreatedAtBetween(
            LocalDateTime.parse(timeBegin, formatter), LocalDateTime.parse(timeEnd, formatter));
    Map<User, Integer> userConsumption = new HashMap<>();
    for (OrderItem orderItem : orderItems) {
      User user = orderItem.getOrder().getUser();
      userConsumption.put(
          user,
          userConsumption.getOrDefault(user, 0)
              + orderItem.getBook().getPrice() * orderItem.getNumber());
    }
    List<Map.Entry<User, Integer>> sortedUserConsumption =
        userConsumption.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(number)
            .toList();
    JSONArray userStatArray = new JSONArray();
    for (Map.Entry<User, Integer> entry : sortedUserConsumption) {
      JSONObject userStat = entry.getKey().toJson();
      userStat.put("consumption", entry.getValue());
      userStatArray.add(userStat);
    }
    return userStatArray;
  }

  @Override
  public JSONObject getMineStat(String timeBegin, String timeEnd, long userId, int number) {
    JSONObject json = new JSONObject();
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) return json;
    Map<Book, Integer> bookSales = new HashMap<>();
    for (Order order : user.getOrders())
      if (order.getCreatedAt().isAfter(LocalDateTime.parse(timeBegin, formatter))
          && order.getCreatedAt().isBefore(LocalDateTime.parse(timeEnd, formatter)))
        for (OrderItem orderItem : order.getItems()) {
          Book book = orderItem.getBook();
          bookSales.put(book, bookSales.getOrDefault(book, 0) + orderItem.getNumber());
        }
    List<Map.Entry<Book, Integer>> sortedBookSales =
        bookSales.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(number)
            .toList();
    JSONArray bookStatArray = new JSONArray();
    for (Map.Entry<Book, Integer> entry : sortedBookSales) {
      JSONObject bookStat = entry.getKey().toJson();
      bookStat.put("sales", entry.getValue());
      bookStatArray.add(bookStat);
    }
    json.put("list", bookStatArray);
    long totalSales = 0, totalConsumption = 0;
    for (Map.Entry<Book, Integer> entry : sortedBookSales) {
      totalSales += entry.getValue();
      totalConsumption += entry.getKey().getPrice() * entry.getValue();
    }
    json.put("totalSales", totalSales);
    json.put("totalConsumption", totalConsumption);
    return json;
  }
}
