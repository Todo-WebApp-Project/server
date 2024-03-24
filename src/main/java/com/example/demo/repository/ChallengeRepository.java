package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.Challenge;

public interface ChallengeRepository  extends JpaRepository<Challenge, Long> {
	// 카테고리로 챌린지 조회
	Page<Challenge> findByCategory(Integer category, Pageable pageable);

	// 검색어로 챌린지 조회 (2가지 방법)
	// 1
	@Query("SELECT c FROM Challenge c WHERE c.tagName LIKE CONCAT('%',:keyword,'%') OR c.tagDesc LIKE CONCAT('%',:keyword,'%')")
    Page<Challenge> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
	// 2
	Page<Challenge> findByTagNameContainingOrTagDescContaining(String tagName, String tagDesc, Pageable pageable); 

}
