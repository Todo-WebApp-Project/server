package com.example.demo.repository;

import com.example.demo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TodoRepository extends JpaRepository<Todo, Integer> {

}