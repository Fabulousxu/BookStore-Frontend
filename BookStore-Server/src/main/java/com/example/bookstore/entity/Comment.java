package com.example.bookstore.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long commentId;

  private long userId;
  private long bookId;
  private String content;

  @Column(name = "`like`")
  private int like = 0;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
