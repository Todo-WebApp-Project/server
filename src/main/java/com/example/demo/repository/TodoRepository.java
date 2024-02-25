package com.example.demo.repository;

import com.example.demo.domain.Todo;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Integer>
{
    List<Todo> findByUserAndTodoDate(User user, LocalDate todo_date);

}