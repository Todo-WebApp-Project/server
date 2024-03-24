package com.example.demo.repository;

import com.example.demo.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    void deleteAllByUserId(String userId);
}
