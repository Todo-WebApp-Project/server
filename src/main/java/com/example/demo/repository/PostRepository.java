package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteByUserId(String userId);
    
    List<Post> findByUserId(String userId);
}
