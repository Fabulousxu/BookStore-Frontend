package com.example.bookstore.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long commentId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  private String content;

  @Column(name = "`like`")
  private int like = 0;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @ManyToMany
  @JoinTable(
      name = "comment_like",
      joinColumns = @JoinColumn(name = "comment_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> likeUsers;

  public Comment(User user, Book book, String content) {
    this.user = user;
    this.book = book;
    this.content = content;
  }

  public JSONObject toJson(User user) {
    JSONObject res = new JSONObject();
    res.put("id", commentId);
    res.put("username", user.getUsername());
    res.put("content", content);
    res.put("createdAt", createdAt);
    res.put("like", like);
    res.put("liked", likeUsers.contains(user));
    return res;
  }
}
