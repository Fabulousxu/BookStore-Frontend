package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  JSONObject login(String username, String password);

  JSONObject register(String username, String email, String password);

  JSONObject searchUsers(String keyword, int pageIndex, int pageSize);

  JSONObject setUserInfo(long userId, String username, String email);

  JSONObject silenceUser(long userId);

  JSONObject unsilenceUser(long userId);
}
