package com.example.winterproject.todoApplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.winterproject.todoApplication.domain.Post;
import com.example.winterproject.todoApplication.domain.Todo;
import com.example.winterproject.todoApplication.repository.PostRepository;
import com.example.winterproject.todoApplication.repository.TodoRepository;

@Service
public class TodoPostService {
	@Autowired
    private TodoRepository todoRepository;

    @Autowired
    private PostRepository postRepository;

    public Todo createOrUpdateTodo(Todo todo) {
        // Todo 엔티티 저장
        Todo savedTodo = todoRepository.save(todo);

        // Post 엔티티 찾기 또는 생성
        Optional<Post> optionalPost = postRepository.findByTodoDate(todo.getTodoDate());
        Post post = optionalPost.orElseGet(() -> new Post());
        post.setTodoDate(todo.getTodoDate());
        // Post에 Todo 추가
        post.getTodos().add(savedTodo);
        savedTodo.setPost(post); // Todo에 Post 설정

        // Post 엔티티 저장
        postRepository.save(post);

        return savedTodo;
    }
}
