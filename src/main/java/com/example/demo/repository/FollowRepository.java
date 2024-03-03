package com.example.demo.repository;

import jakarta.transaction.Transactional;
import com.example.demo.domain.Follow;
import com.example.demo.domain.ck.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findFollowingByFollowed(String followed);

    List<Follow> findFollowedByFollowing(String following);

//    @Transactional
//    @Modifying
//    @Query(value = "delete from Follow where following = :user_id or followed = :user_id", nativeQuery = true)
//    void deleteAllByIds(@Param("user_id") String user_id);

    Long deleteByFollowing(String following);
    Long deleteByFollowed(String followed);
}
