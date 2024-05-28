package com.example.bookstore.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "`order`")
@Getter
@Setter
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long orderId;

  private long userId;
  private String receiver;
  private String address;
  private String tel;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
