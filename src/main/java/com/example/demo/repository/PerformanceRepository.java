package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Performance;


public interface PerformanceRepository extends JpaRepository<Performance,Long>
{
    @Query("SELECT MAX(p.perform_id) FROM Performance p")
    Optional<Long> findMaxPerformId();
    
	// select count(*) from performance where userId = id and finish = 1
	@Query(value = "SELECT count(*) FROM performance where user_id = :userId AND finish = 1",
			nativeQuery = true)
	int countByUserIdAndFinish(String userId);
}
