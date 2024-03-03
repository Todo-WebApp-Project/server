package org.example.test1.repository;

import org.example.test1.domain.Like;
import org.example.test1.domain.ck.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    void deleteByUserId(String userId);
}
