package com.example.bookstore.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  private String username;
  private String nickname = "";
  private String email;
  private long balance = 0;
  private Boolean admin = false;
  private Boolean silence = false;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("createdAt DESC")
  private List<Order> orders;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("cartItemId DESC")
  private List<CartItem> cart;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @ManyToMany(mappedBy = "likeUsers", cascade = CascadeType.ALL)
  private List<Comment> likeComments;

  public User(String username, String email) {
    this.username = username;
    this.email = email;
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", userId);
    json.put("username", username);
    json.put("nickname", nickname);
    json.put("email", email);
    json.put("balance", balance);
    json.put("silence", silence);
    return json;
  }
}
