package com.example.winterproject.todoApplication.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.winterproject.todoApplication.domain.Post;
import com.example.winterproject.todoApplication.dto.PostResponse;
import com.example.winterproject.todoApplication.repository.PostRepository;

@RestController
public class PostResource {
	
	private PostRepository postRepository;
	
	@Autowired
	public PostResource(PostRepository postRepository) {
		this.postRepository = postRepository;	 
	}
	 
	 /*
	  * 다른 사람들 Todo 보기
	  */
	 // GET /get_posts 
	 @GetMapping("/get_posts")
	 public List<PostResponse> getPosts() {
	    List<Post> posts = postRepository.findAll();
	    return posts.stream().map(post -> {
	        PostResponse response = new PostResponse();
	        response.setPostId(post.getPostId());
	        response.setUserName(post.getUser().getName());
	        response.setTodoDate(post.getTodoDate());
	        
	        List<PostResponse.TaskResponse> taskResponses = post.getTodos().stream()
	                .map(todo -> new PostResponse.TaskResponse(todo.getTask(), todo.getFinish()))
	                .collect(Collectors.toList());
	        response.setTasks(taskResponses);
	        return response;
	    }).collect(Collectors.toList());
	}
	
}
