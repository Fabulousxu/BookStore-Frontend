package com.example.bookstore.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long orderItemId;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  private int number;

  public OrderItem(Order order, Book book, int number) {
    this.order = order;
    this.book = book;
    this.number = number;
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", orderItemId);
    json.put("book", book.toJson());
    json.put("number", number);
    return json;
  }

  public JSONObject toJsonWithOrderMessage() {
    JSONObject json = toJson();
    json.put("username", order.getUser().getUsername());
    json.put("receiver", order.getReceiver());
    json.put("address", order.getAddress());
    json.put("tel", order.getTel());
    json.put("createdAt", order.getCreatedAt());
    return json;
  }
}
