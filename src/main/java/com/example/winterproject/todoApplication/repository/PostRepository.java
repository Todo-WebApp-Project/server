package com.example.winterproject.todoApplication.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.winterproject.todoApplication.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

	Optional<Post> findByTodoDate(LocalDate todoDate);

}
