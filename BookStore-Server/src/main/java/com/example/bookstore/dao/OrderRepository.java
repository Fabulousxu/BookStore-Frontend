package com.example.bookstore.dao;

import com.example.bookstore.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUserIdOrderByOrderIdDesc(long userId);
}
