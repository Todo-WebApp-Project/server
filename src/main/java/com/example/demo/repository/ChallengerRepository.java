package com.example.demo.repository;

import com.example.demo.domain.Challenger;
import com.example.demo.domain.ck.ChallengerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengerRepository  extends JpaRepository<Challenger, ChallengerId> {
    void deleteByUserId(String userId);
}
