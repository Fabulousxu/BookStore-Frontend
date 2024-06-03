package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.*;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.util.Util;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  @Autowired private UserRepository userRepository;
  @Autowired private BookRepository bookRepository;
  @Autowired private OrderItemRepository orderItemRepository;

  @Override
  public JSONArray getOrder(long userId) {
    JSONArray res = new JSONArray();
    User user = userRepository.findById(userId).orElse(null);
    if (user != null) for (Order order : user.getOrders()) res.add(order.toJson());
    return res;
  }

  @Override
  public JSONArray searchOrderItems(long userId, String keyword, int pageIndex, int pageSize) {
    JSONArray res = new JSONArray();
    User user = userRepository.findById(userId).orElse(null);
    if (user != null) {
      Page<OrderItem> orderItemPage =
          orderItemRepository
              .findByOrder_User_UsernameContainsOrOrder_User_NicknameContainsOrOrder_User_EmailContainsOrOrder_ReceiverContainsOrOrder_AddressContainsOrOrder_TelContainsOrBook_TitleContainsOrBook_AuthorContainsOrBook_IsbnContainsOrBook_DescriptionContains(
                  user.getUsername(),
                  "",
                  "",
                  "",
                  keyword,
                  keyword,
                  keyword,
                  keyword,
                  keyword,
                  keyword,
                  PageRequest.of(pageIndex, pageSize));
      for (OrderItem orderItem : orderItemPage) res.add(orderItem.toJsonWithOrderMessage());
    }
    return res;
  }

  @Override
  public JSONObject placeOrder(
      List<Long> cartItemIds, long userId, String receiver, String address, String tel) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) return Util.errorResponseJson("用户不存在");
    for (long cartItemId : cartItemIds)
      if (user.getCart().stream().noneMatch(item -> item.getCartItemId() == cartItemId))
        return Util.errorResponseJson("购物车商品错误");
    Order order = new Order(user, receiver, address, tel);
    for (long cartItemId : cartItemIds) {
      CartItem cartItem =
          user.getCart().stream()
              .filter(item -> item.getCartItemId() == cartItemId)
              .findFirst()
              .get();
      Book book = cartItem.getBook();
      book.setSales(book.getSales() + cartItem.getNumber());
      order.getItems().add(new OrderItem(order, book, cartItem.getNumber()));
      user.getCart().remove(cartItem);
      bookRepository.save(book);
    }
    user.getOrders().add(order);
    userRepository.save(user);
    return Util.successResponseJson("下单成功!");
  }

  @Override
  public JSONObject searchOrderItems(String keyword, int pageIndex, int pageSize) {
    JSONObject res = new JSONObject();
    Page<OrderItem> orderItemPage =
        orderItemRepository
            .findByOrder_User_UsernameContainsOrOrder_User_NicknameContainsOrOrder_User_EmailContainsOrOrder_ReceiverContainsOrOrder_AddressContainsOrOrder_TelContainsOrBook_TitleContainsOrBook_AuthorContainsOrBook_IsbnContainsOrBook_DescriptionContains(
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                PageRequest.of(pageIndex, pageSize));
    res.put("totalNumber", orderItemPage.getTotalElements());
    res.put("totalPage", orderItemPage.getTotalPages());
    JSONArray items = new JSONArray();
    for (OrderItem orderItem : orderItemPage) items.add(orderItem.toJsonWithOrderMessage());
    res.put("items", items);
    return res;
  }
}
