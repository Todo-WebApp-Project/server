package com.example.winterproject.todoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.winterproject.todoApplication.domain.Like;
import com.example.winterproject.todoApplication.domain.LikeId;


public interface LikeRepository extends JpaRepository<Like, LikeId>{
	// 특정 postId에 대한 좋아요 수 조회
	//@Query("SELECT COUNT(l) FROM Like l WHERE l.likeId.todoId = :todoId")
    //long countByTodoId(@Param("todoId") Integer todoId);
}
