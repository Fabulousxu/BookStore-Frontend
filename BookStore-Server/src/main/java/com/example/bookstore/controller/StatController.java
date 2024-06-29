package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/stat")
public class StatController {
  @Autowired private StatService statService;

  @GetMapping("/book")
  public JSONArray getBookStat(String timeBegin, String timeEnd) {
    return statService.getBookStat(timeBegin, timeEnd, 100);
  }

  @GetMapping("/user")
  public JSONArray getUserStat(String timeBegin, String timeEnd) {
    return statService.getUserStat(timeBegin, timeEnd, 100);
  }

  @GetMapping("/mine")
  public JSONObject getMineStat(
      String timeBegin, String timeEnd, @SessionAttribute("id") long userId) {
    return statService.getMineStat(timeBegin, timeEnd, userId, 100);
  }
}
