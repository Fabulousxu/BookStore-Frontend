package com.example.bookstore.entity;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long orderId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String receiver;
  private String address;
  private String tel;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> items;

  public Order(User user, String receiver, String address, String tel) {
    this.user = user;
    this.receiver = receiver;
    this.address = address;
    this.tel = tel;
    items = new ArrayList<>();
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", orderId);
    json.put("receiver", receiver);
    json.put("address", address);
    json.put("tel", tel);
    json.put("createdAt", createdAt);
    json.put("username", user.getUsername());
    JSONArray items = new JSONArray();
    for (OrderItem item : this.items) items.add(item.toJson());
    json.put("items", items);
    return json;
  }
}
