package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long cartItemId;

  private long userId;
  private long bookId;
  private int number;
}
