package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
  @Id private long userId;
  private String username;
  private String password;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}
