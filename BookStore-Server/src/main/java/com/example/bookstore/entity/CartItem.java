package com.example.bookstore.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long cartItemId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  private int number = 1;

  public CartItem(User user, Book book) {
    this.user = user;
    this.book = book;
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", cartItemId);
    json.put("book", book.toJson());
    json.put("number", number);
    return json;
  }
}
