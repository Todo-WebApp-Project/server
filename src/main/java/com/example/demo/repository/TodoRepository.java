package com.example.demo.repository;

import com.example.demo.domain.Todo;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface TodoRepository extends JpaRepository<Todo, Integer>
{
    List<Todo> findByUserAndTodoDate(User user, LocalDate todo_date);
    
	// User ID와 todoDate를 기준으로 Todo를 조회하는 메소드
	List<Todo> findByUserIdAndTodoDate(String userId, java.util.Date postDate);

}