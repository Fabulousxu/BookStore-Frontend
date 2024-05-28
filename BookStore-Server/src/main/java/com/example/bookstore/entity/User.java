package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {
  @Id private long userId;
  private long balance;
}
