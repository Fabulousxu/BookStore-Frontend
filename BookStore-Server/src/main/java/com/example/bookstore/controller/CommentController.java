package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
  @Autowired private CommentService commentService;

  @PutMapping("/{id}/like")
  public JSONObject like(@PathVariable long id, @SessionAttribute("id") long userId) {
    return commentService.like(id, userId);
  }

  @PutMapping("/{id}/unlike")
  public JSONObject unlike(@PathVariable long id, @SessionAttribute("id") long userId) {
    return commentService.unlike(id, userId);
  }
}
