package com.example.bookstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_auth")
public class UserAuth {
  @Id private long userId;
  private String password;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}
