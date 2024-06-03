package com.example.bookstore.repository;

import com.example.bookstore.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
  boolean existsByUser_UsernameAndPassword(String username, String password);
}
