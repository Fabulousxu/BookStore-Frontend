package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  private String nickname;
  private String password;
}
