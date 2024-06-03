package com.example.bookstore.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long bookId;

  private String title;
  private String author;
  private String isbn;
  private String cover;
  private String description;
  private int price;
  private int sales = 0;
  private int repertory = 0;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("createdAt DESC")
  private List<Comment> comments;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> cartItems;

  public Book(
      String title,
      String author,
      String isbn,
      String cover,
      String description,
      int price,
      int sales,
      int repertory) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.cover = cover;
    this.description = description;
    this.price = price;
    this.sales = sales;
    this.repertory = repertory;
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", bookId);
    json.put("title", title);
    json.put("author", author);
    json.put("isbn", isbn);
    json.put("cover", cover);
    json.put("description", description);
    json.put("price", price);
    json.put("sales", sales);
    json.put("repertory", repertory);
    return json;
  }
}
