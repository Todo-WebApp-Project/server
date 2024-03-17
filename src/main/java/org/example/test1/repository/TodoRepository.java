package org.example.test1.repository;

import java.sql.Date;
import java.util.List;

import org.example.test1.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	// User ID와 todoDate를 기준으로 Todo를 조회하는 메소드
	List<Todo> findByUserIdAndTodoDate(String userId, java.util.Date postDate);
}
