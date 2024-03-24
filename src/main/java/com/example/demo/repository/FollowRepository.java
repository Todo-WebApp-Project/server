package com.example.demo.repository;

import com.example.demo.domain.Follow;
import com.example.demo.domain.ck.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findFollowingByFollowed(String followed);

    List<Follow> findFollowedByFollowing(String following);

    @Modifying
    @Query(value = "delete from Follow where following = :user_id or followed = :user_id")
    void deleteWithQuery(@Param("user_id") String user_id);
}
