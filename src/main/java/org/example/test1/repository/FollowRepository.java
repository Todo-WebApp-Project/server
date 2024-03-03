package org.example.test1.repository;

import jakarta.transaction.Transactional;
import org.example.test1.domain.Follow;
import org.example.test1.domain.User;
import org.example.test1.domain.ck.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
