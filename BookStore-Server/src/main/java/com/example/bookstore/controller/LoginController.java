package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class LoginController {
  @Autowired private UserService userService;

  @PostMapping
  public JSONObject login(@RequestBody JSONObject body, HttpSession session) {
    return userService.login(body.getString("username"), body.getString("password"), session);
  }
}
