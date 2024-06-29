package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.User;
import com.example.bookstore.entity.UserAuth;
import com.example.bookstore.repository.UserAuthRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;
import com.example.bookstore.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private UserRepository userRepository;
  @Autowired private UserAuthRepository userAuthRepository;
  @Autowired private HttpSession session;

  @Override
  public JSONObject login(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user == null) return Util.errorResponseJson("用户不存在");
    if (!userAuthRepository.existsByUser_UsernameAndPassword(username, password))
      return Util.errorResponseJson("密码错误");
    if (user.getSilence()) return Util.errorResponseJson("您的账户已被禁用");
    session.setAttribute("id", user.getUserId());
    JSONObject res = Util.successResponseJson("登录成功"), data = new JSONObject();
    data.put("admin", user.getAdmin());
    res.put("data", data);
    return res;
  }

  @Override
  public JSONObject register(String username, String email, String password) {
    if (userRepository.existsByUsername(username)) return Util.errorResponseJson("用户已存在");
    User user = new User(username, email);
    userRepository.save(user);
    userAuthRepository.save(new UserAuth(user, password));
    return Util.successResponseJson("注册成功");
  }

  @Override
  public JSONObject searchUsers(String keyword, int pageIndex, int pageSize) {
    JSONObject res = new JSONObject();
    Page<User> userPage =
        userRepository.findByUsernameContainsOrNicknameContainsOrEmailContains(
            keyword, keyword, keyword, PageRequest.of(pageIndex, pageSize));
    res.put("totalNumber", userPage.getTotalElements());
    res.put("totalPage", userPage.getTotalPages());
    JSONArray items = new JSONArray();
    for (User user : userPage) items.add(user.toJson());
    res.put("items", items);
    return res;
  }

  @Override
  public JSONObject setUserInfo(long userId, String username, String email) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) return Util.errorResponseJson("用户不存在");
    user.setUsername(username);
    user.setEmail(email);
    userRepository.save(user);
    return Util.successResponseJson("修改成功");
  }

  @Override
  public JSONObject silenceUser(long userId) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) return Util.errorResponseJson("用户不存在");
    if (user.getSilence()) return Util.errorResponseJson("用户已被禁用");
    user.setSilence(true);
    userRepository.save(user);
    return Util.successResponseJson("禁用成功");
  }

  @Override
  public JSONObject unsilenceUser(long userId) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) return Util.errorResponseJson("用户不存在");
    if (!user.getSilence()) return Util.errorResponseJson("用户未被禁用");
    user.setSilence(false);
    userRepository.save(user);
    return Util.successResponseJson("解禁成功");
  }
}
