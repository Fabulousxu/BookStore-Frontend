package com.example.bookstore.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface StatService {
  JSONArray getBookStat(String timeBegin, String timeEnd, int number);

  JSONArray getUserStat(String timeBegin, String timeEnd, int number);

  JSONObject getMineStat(String timeBegin, String timeEnd, long userId, int number);
}
