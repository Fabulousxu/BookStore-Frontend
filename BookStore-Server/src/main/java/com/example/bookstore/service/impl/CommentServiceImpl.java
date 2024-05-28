package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.AccountRepository;
import com.example.bookstore.dao.BookRepository;
import com.example.bookstore.dao.CommentLikeRepository;
import com.example.bookstore.dao.CommentRepository;
import com.example.bookstore.entity.Comment;
import com.example.bookstore.entity.CommentLike;
import com.example.bookstore.entity.idclass.CommentLikeId;
import com.example.bookstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
  @Autowired private AccountRepository accountRepository;
  @Autowired private BookRepository bookRepository;
  @Autowired private CommentRepository commentRepository;
  @Autowired private CommentLikeRepository commentLikeRepository;

  public JSONObject toJson(Comment comment, long userId) {
    JSONObject json = new JSONObject();
    json.put("id", comment.getCommentId());
    accountRepository
        .findById(comment.getUserId())
        .ifPresentOrElse(
            account -> {
              json.put("username", account.getNickname());
            },
            () -> {
              json.put("username", "账号已注销");
            });
    json.put("content", comment.getContent());
    json.put("reply", "");
    json.put("like", comment.getLike());
    json.put(
        "liked",
        commentLikeRepository.existsById(new CommentLikeId(comment.getCommentId(), userId)));
    json.put("createdAt", comment.getCreatedAt());
    return json;
  }

  public boolean addComment(long userId, long bookId, String content) {
    if (!bookRepository.existsById(bookId)) return false;
    Comment comment = new Comment();
    comment.setUserId(userId);
    comment.setBookId(bookId);
    comment.setContent(content);
    commentRepository.save(comment);
    return true;
  }

  public boolean likeComment(long commentId, long userId) {
    if (commentLikeRepository.existsById(new CommentLikeId(commentId, userId))) return false;
    Comment comment = commentRepository.findById(commentId).orElse(null);
    if (comment == null) return false;
    else {
      commentLikeRepository.save(new CommentLike(commentId, userId));
      comment.setLike(comment.getLike() + 1);
      commentRepository.save(comment);
      return true;
    }
  }

  public boolean unlikeComment(long commentId, long userId) {
    CommentLikeId commentLikeId = new CommentLikeId(commentId, userId);
    if (!commentLikeRepository.existsById(commentLikeId)) return false;
    Comment comment = commentRepository.findById(commentId).orElse(null);
    if (comment == null) return false;
    else {
      commentLikeRepository.deleteById(commentLikeId);
      comment.setLike(comment.getLike() - 1);
      commentRepository.save(comment);
      return true;
    }
  }
}
