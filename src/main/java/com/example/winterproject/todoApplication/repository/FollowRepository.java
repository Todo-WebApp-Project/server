package com.example.winterproject.todoApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.winterproject.todoApplication.domain.Follow;
import com.example.winterproject.todoApplication.domain.FollowId;

public interface FollowRepository extends JpaRepository<Follow, FollowId > {

	// follower를 기준으로 팔로잉 목록 조회
    List<Follow> findByFollowIdFollowing(String followingUserId);
    
    // following을 기준으로 팔로워 목록 조회
    List<Follow> findByFollowIdFollowed(String followedUserId);

    // userId가 팔로우하는 사용자(팔로잉)의 수를 조회
    long countByFollowIdFollowing(String userId);

    // userId를 팔로우하는 사용자(팔로워)의 수를 조회
    long countByFollowIdFollowed(String userId);
}
