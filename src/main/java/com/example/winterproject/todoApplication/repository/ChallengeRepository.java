package com.example.winterproject.todoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.winterproject.todoApplication.domain.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>{

}
