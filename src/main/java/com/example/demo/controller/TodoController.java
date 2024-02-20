package com.example.demo.controller;


import com.example.demo.domain.Todo;
import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.domain.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TodoController {

	private UserRepository userRepository;
	
	private TodoRepository todoRepository;

	public TodoController(UserRepository userRepository, TodoRepository todoRepository) {
		this.userRepository = userRepository;
		this.todoRepository = todoRepository;
	}

	@GetMapping("/users") // find User all
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	
	//http://localhost:8080/users
	
	//EntityModel
	//WebMvcLinkBuilder
	// find one
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	

	// (2)Todo
	//one User -> all posting
	@GetMapping("/users/{id}/todos")
	public List<Todo> retrievePostsForUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		return user.get().getTodos();

	}
	// delete user
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	

	
	//Create User
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		
		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();   
		
		return ResponseEntity.created(location).build();
	}

	//create Todo
	// 
	@PostMapping("/users/{id}/todos")
	public ResponseEntity<Object> createTodoForUser(@PathVariable int id, @Valid @RequestBody Todo todo) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		todo.setUser(user.get());
		
		Todo savedTodo = todoRepository.save(todo);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedTodo.getId())
				.toUri();   

		return ResponseEntity.created(location).build();

	}
	
	//delete Todo
	@DeleteMapping("/users/{userId}/todos/{todoId}")
	public ResponseEntity<?> deleteTodo(@PathVariable int userId, @PathVariable int todoId) {
	    //find user_id
		Optional<User> userOptional = userRepository.findById(userId);
	    if (userOptional.isEmpty()) {
	        throw new UserNotFoundException("id:" + userId);
	    }
	    //find to exist todo_id
	    Optional<Todo> todoOptional = todoRepository.findById(todoId);
	    if (todoOptional.isEmpty()) {
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    Todo todo = todoOptional.get();
	    if (todo.getUser().getId() != userId) {
	        // todo가 해당 유저에 속하지 않는 경우에 대한 예외 처리
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    todoRepository.deleteById(todoId);
	    
	    return ResponseEntity.noContent().build();
	}
	
	//Update Todo
	@PutMapping("/users/{userId}/todos/{todoId}")
	public ResponseEntity<?> updateTodo(@PathVariable int userId, @PathVariable int todoId, @Valid @RequestBody Todo updatedTodo) {
	    //Exception
		Optional<User> userOptional = userRepository.findById(userId);
	    if (userOptional.isEmpty()) {
	        throw new UserNotFoundException("id:" + userId);
	    }
	  
	    Optional<Todo> todoOptional = todoRepository.findById(todoId);
	    if (todoOptional.isEmpty()) {
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    Todo todo = todoOptional.get();
	    if (todo.getUser().getId() != userId) {
	        
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    // update todo
	  //todo_id , user_id, task, finish, no_tag. share , todo_date
	    todo.setTask(updatedTodo.getTask());
	    todo.setNo_tag(updatedTodo.getNo_tag());
	    todo.setShare(updatedTodo.getShare());
	    todo.setTodoDate(updatedTodo.getTodoDate());
	    todoRepository.save(todo);    
	    return ResponseEntity.ok().build();
	}
	
	// todo finished T/F function
	@PatchMapping("/users/{userId}/todos/{todoId}/todoChecking")
	public ResponseEntity<?> togglePostFinished(@PathVariable int userId,@PathVariable int todoId) {
	    Optional<Todo> todoOptional = todoRepository.findById(todoId);
	    
	    if(todoOptional.isEmpty()) {
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	 // changed 0(x), 1(done) finished
	    Todo todo = todoOptional.get();
	    int oldState = todo.getFinish();
	    int newState = 0;
	    switch(oldState) {
	    	case 0: newState = 1;
	    		break;
	    	case 1 : newState= 0;
	    		break;
	    	
	    }
	    todo.setFinish(newState);
	    todoRepository.save(todo);
	    
	    return ResponseEntity.ok().build();
	}
	

	
}