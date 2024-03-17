package org.example.test1.repository;

import org.example.test1.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository  extends JpaRepository<Challenge, Long> {

	
}

