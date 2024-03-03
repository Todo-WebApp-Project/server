package org.example.test1.repository;

import org.example.test1.domain.Challenger;
import org.example.test1.domain.ck.ChallengerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengerRepository  extends JpaRepository<Challenger, ChallengerId> {
    void deleteByUserId(String userId);
}
