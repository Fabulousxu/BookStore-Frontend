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
    JSONObject res = new JSONObject();
    if (commentService.likeComment(id, userId)) {
      res.put("ok", true);
      res.put("message", "点赞成功");
    } else {
      res.put("ok", false);
      res.put("message", "评论已点赞");
    }
    return res;
  }

  @PutMapping("/{id}/unlike")
  public JSONObject unlike(@PathVariable long id, @SessionAttribute("id") long userId) {
    JSONObject res = new JSONObject();
    if (commentService.unlikeComment(id, userId)) {
      res.put("ok", true);
      res.put("message", "取消点赞成功");
    } else {
      res.put("ok", false);
      res.put("message", "评论未点赞");
    }
    return res;
  }
}
