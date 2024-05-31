package com.example.bookstore.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Setter
@Getter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  private long balance;

  @OneToOne(mappedBy = "user")
  private Account account;

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
}
