package com.example.winterproject.todoApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.winterproject.todoApplication.domain.User;

public interface UserRepository extends JpaRepository<User, String>{
	 Optional<User> findByEmail(String email);
}
