package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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
}
