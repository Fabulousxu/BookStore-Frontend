package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_auth")
@NoArgsConstructor
public class UserAuth {
  @Id private long userId;
  private String password;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public UserAuth(User user, String password) {
    this.userId = user.getUserId();
    this.password = password;
  }
}
