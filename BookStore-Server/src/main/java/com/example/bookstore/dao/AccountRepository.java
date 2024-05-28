package com.example.bookstore.dao;

import com.example.bookstore.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByNickname(String username);
}
