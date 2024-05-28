package com.example.bookstore.dao;

import com.example.bookstore.entity.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findByUserIdOrderByCartItemIdDesc(long userId);

  CartItem findByUserIdAndBookId(long userId, long bookId);
}
