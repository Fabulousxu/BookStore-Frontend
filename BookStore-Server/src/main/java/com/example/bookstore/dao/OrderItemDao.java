package com.example.bookstore.dao;

import com.example.bookstore.entity.OrderItem;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Long> {
  Page<OrderItem>
      findByOrder_User_UsernameContainsOrOrder_User_NicknameContainsOrOrder_User_EmailContainsOrOrder_ReceiverContainsOrOrder_AddressContainsOrOrder_TelContainsOrBook_TitleContainsOrBook_AuthorContainsOrBook_IsbnContainsOrBook_DescriptionContains(
          String username,
          String nickname,
          String email,
          String receiver,
          String address,
          String tel,
          String title,
          String author,
          String isbn,
          String description,
          Pageable pageable);

  List<OrderItem>
      findAllByOrder_User_UsernameContainsOrOrder_User_NicknameContainsOrOrder_User_EmailContainsOrOrder_ReceiverContainsOrOrder_AddressContainsOrOrder_TelContainsOrBook_TitleContainsOrBook_AuthorContainsOrBook_IsbnContainsOrBook_DescriptionContains(
          String username,
          String nickname,
          String email,
          String receiver,
          String address,
          String tel,
          String title,
          String author,
          String isbn,
          String description);

  List<OrderItem> findByOrder_CreatedAtBetween(LocalDateTime timeBegin, LocalDateTime timeEnd);
}
