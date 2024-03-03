package org.example.test1.repository;

import org.example.test1.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteByUserId(String userId);
}
