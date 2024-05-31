package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.CommentRepository;
import com.example.bookstore.dao.UserRepository;
import com.example.bookstore.entity.Comment;
import com.example.bookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
  @Autowired private UserRepository userRepository;
  @Autowired private CommentRepository commentRepository;

  @PutMapping("/{id}/like")
  public JSONObject like(@PathVariable long id, @SessionAttribute("id") long userId) {
    JSONObject res = new JSONObject();
    User user = userRepository.findById(userId).get();
    Comment comment = commentRepository.findById(id).orElse(null);
    if (comment == null) {
      res.put("ok", false);
      res.put("message", "评论不存在");
    } else if (user.getLikeComments().contains(comment)) {
      res.put("ok", false);
      res.put("message", "评论已点赞");
    } else {
      comment.getLikeUsers().add(user);
      comment.setLike(comment.getLike() + 1);
      commentRepository.save(comment);
      res.put("ok", true);
      res.put("message", "点赞成功");
    }
    return res;
  }

  @PutMapping("/{id}/unlike")
  public JSONObject unlike(@PathVariable long id, @SessionAttribute("id") long userId) {
    JSONObject res = new JSONObject();
    User user = userRepository.findById(userId).get();
    Comment comment = commentRepository.findById(id).orElse(null);
    if (comment == null) {
      res.put("ok", false);
      res.put("message", "评论不存在");
    } else if (!user.getLikeComments().contains(comment)) {
      res.put("ok", false);
      res.put("message", "评论未点赞");
    } else {
      comment.getLikeUsers().remove(user);
      comment.setLike(comment.getLike() - 1);
      commentRepository.save(comment);
      res.put("ok", true);
      res.put("message", "取消点赞成功");
    }
    return res;
  }
}
