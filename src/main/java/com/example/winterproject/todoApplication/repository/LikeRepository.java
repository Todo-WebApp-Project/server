package com.example.winterproject.todoApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.winterproject.todoApplication.domain.Like;
import com.example.winterproject.todoApplication.domain.LikeId;
import com.example.winterproject.todoApplication.domain.User;

public interface LikeRepository extends JpaRepository<Like, LikeId>{

}
