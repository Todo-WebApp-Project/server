package com.example.winterproject.todoApplication.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.winterproject.todoApplication.domain.Todo;
import com.example.winterproject.todoApplication.dto.PostResponse;
import com.example.winterproject.todoApplication.repository.PostRepository;
import com.example.winterproject.todoApplication.repository.TodoRepository;

@RestController
public class TodoResource {
	
	private TodoRepository todoRepository;
	
	 public TodoResource(TodoRepository todoRepository) {
		 this.todoRepository = todoRepository;
	 }
	 
	 /*
	  * 다른 사람들 Todo 보기
	  */
	 // GET /get_posts 
	
}
