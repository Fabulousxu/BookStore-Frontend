package com.example.bookstore.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long bookId;

  private String title;
  private String author;
  private String description;
  private int price;
  private String cover;
  private int sales;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  @OrderBy("createdAt DESC")
  private List<Comment> comments;

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", bookId);
    json.put("title", title);
    json.put("author", author);
    json.put("description", description);
    json.put("price", price);
    json.put("cover", cover);
    json.put("sales", sales);
    return json;
  }
}
