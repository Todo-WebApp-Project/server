package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {


    @Query(value = "SELECT user_id FROM users", nativeQuery = true)
    List<String> findAllUserIds();

    Optional<User> findByEmail(String email);

}