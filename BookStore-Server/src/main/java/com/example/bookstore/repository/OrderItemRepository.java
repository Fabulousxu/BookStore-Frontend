package com.example.bookstore.repository;

import com.example.bookstore.entity.OrderItem;
import com.example.bookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
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

  Page<OrderItem>
      findByOrder_UserAndOrder_ReceiverContainsOrOrder_UserAndOrder_AddressContainsOrOrder_UserAndOrder_TelContainsOrOrder_UserAndBook_TitleContainsOrOrder_UserAndBook_AuthorContainsOrOrder_UserAndBook_IsbnContainsOrOrder_UserAndBook_DescriptionContains(
          User user1,
          String receiver,
          User user2,
          String address,
          User user3,
          String tel,
          User user4,
          String title,
          User user5,
          String author,
          User user6,
          String isbn,
          User user7,
          String description,
          Pageable pageable);
}
