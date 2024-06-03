package com.example.bookstore.util;

import com.alibaba.fastjson2.JSONObject;

public class Util {
  public static JSONObject successResponseJson(String message) {
    JSONObject res = new JSONObject();
    res.put("ok", true);
    res.put("message", message);
    return res;
  }

  public static JSONObject errorResponseJson(String message) {
    JSONObject res = new JSONObject();
    res.put("ok", false);
    res.put("message", message);
    return res;
  }
}
