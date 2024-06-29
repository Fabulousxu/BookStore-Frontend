package com.example.bookstore.dao;

import com.example.bookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
  User findByUsername(String username);

  boolean existsByUsername(String username);

  Page<User> findByUsernameContainsOrNicknameContainsOrEmailContains(
      String username, String nickname, String email, Pageable pageable);
}
