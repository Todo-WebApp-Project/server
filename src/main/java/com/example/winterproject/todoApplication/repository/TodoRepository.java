package com.example.winterproject.todoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.winterproject.todoApplication.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{

}
