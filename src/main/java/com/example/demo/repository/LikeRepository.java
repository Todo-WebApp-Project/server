package com.example.demo.repository;

import com.example.demo.domain.Like;
import com.example.demo.domain.ck.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    void deleteAllByUserId(String userId);
}
