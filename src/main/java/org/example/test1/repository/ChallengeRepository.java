package org.example.test1.repository;

import org.example.test1.domain.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository  extends JpaRepository<Challenge, Long> {
	// 카테고리로 챌린지 조회
	Page<Challenge> findByCategory(Integer category, Pageable pageable);

	
}

