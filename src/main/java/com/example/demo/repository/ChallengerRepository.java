package com.example.demo.repository;

import com.example.demo.domain.Challenger;
import com.example.demo.domain.ck.ChallengerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChallengerRepository  extends JpaRepository<Challenger, ChallengerId> {
    void deleteAllByUserId(String userId);
}
