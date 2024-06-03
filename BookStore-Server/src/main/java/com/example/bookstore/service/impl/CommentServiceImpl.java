package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.Comment;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.CommentRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.CommentService;
import com.example.bookstore.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
  @Autowired private UserRepository userRepository;
  @Autowired private CommentRepository commentRepository;

  @Override
  public JSONObject like(long commentId, long userId) {
    Comment comment = commentRepository.findById(commentId).orElse(null);
    User user = userRepository.findById(userId).orElse(null);
    if (comment == null) return Util.errorResponseJson("评论不存在");
    if (user == null) return Util.errorResponseJson("用户不存在");
    if (user.getLikeComments().contains(comment)) return Util.errorResponseJson("评论已点赞");
    comment.getLikeUsers().add(user);
    comment.setLike(comment.getLike() + 1);
    commentRepository.save(comment);
    return Util.successResponseJson("点赞成功");
  }

  @Override
  public JSONObject unlike(long commentId, long userId) {
    Comment comment = commentRepository.findById(commentId).orElse(null);
    User user = userRepository.findById(userId).orElse(null);
    if (comment == null) return Util.errorResponseJson("评论不存在");
    if (user == null) return Util.errorResponseJson("用户不存在");
    if (!user.getLikeComments().contains(comment)) return Util.errorResponseJson("评论未点赞");
    comment.getLikeUsers().remove(user);
    comment.setLike(comment.getLike() - 1);
    commentRepository.save(comment);
    return Util.successResponseJson("取消点赞成功");
  }
}
