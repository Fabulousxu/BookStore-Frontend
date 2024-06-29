package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {
  @Autowired private UserService userService;

  @PostMapping("/login")
  public JSONObject login(@RequestBody JSONObject body) {
    return userService.login(body.getString("username"), body.getString("password"));
  }

  @PostMapping("/register")
  public JSONObject register(@RequestBody JSONObject body) {
    return userService.register(
        body.getString("username"), body.getString("email"), body.getString("password"));
  }
}
