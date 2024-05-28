package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.dao.AccountRepository;
import com.example.bookstore.entity.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class LoginController {
  @Autowired private AccountRepository accountRepository;

  @PostMapping
  public JSONObject login(@RequestBody JSONObject param, HttpSession session) {
    JSONObject res = new JSONObject();
    Account account = accountRepository.findByNickname(param.getString("username"));
    if (account == null) {
      res.put("ok", false);
      res.put("message", "用户不存在");
    } else if (!account.getPassword().equals(param.getString("password"))) {
      res.put("ok", false);
      res.put("message", "密码错误");
    } else {
      session.setAttribute("id", account.getUserId());
      res.put("ok", true);
      res.put("message", "登录成功");
    }
    return res;
  }
}
