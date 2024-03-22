package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Challenge;

public interface ChallengeRepository  extends JpaRepository<Challenge, Long> {
	// 카테고리로 챌린지 조회
	Page<Challenge> findByCategory(Integer category, Pageable pageable);

}
