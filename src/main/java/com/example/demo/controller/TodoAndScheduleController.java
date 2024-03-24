package com.example.demo.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.repository.*;
import com.example.demo.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.exception.ScheduleNotFoundException;
import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.TodoRepository;

import jakarta.validation.Valid;



@RestController
public class TodoAndScheduleController {

	private UserRepository userRepository;
	
	private TodoRepository todoRepository;

	private ScheduleRepository schRepository;

	public TodoAndScheduleController(UserRepository userRepository, TodoRepository todoRepository, ScheduleRepository schRepository ) {
		this.userRepository = userRepository;
		this.todoRepository = todoRepository;
		this.schRepository = schRepository;
	}

	// (2)Todo
	//one User -> all posting
	@GetMapping("/users/{id}/todos")
	public List<Todo> retrievePostsForUser(@PathVariable String id) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		return user.get().getTodos();

	}
	// delete user
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable String id) {
		userRepository.deleteById(id);
	}
	

	
	//Create User
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		
		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getUserId())
						.toUri();   
		
		return ResponseEntity.created(location).build();
	}

	//create Todo
	// 
	@PostMapping("/users/{id}/todos")
	public ResponseEntity<Object> createTodoForUser(@PathVariable String id, @Valid @RequestBody Todo todo) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);

		Todo savedTodo = todoRepository.save(todo);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedTodo.getUserId())
				.toUri();   

		return ResponseEntity.created(location).build();

	}
	
	//delete Todo
	@DeleteMapping("/users/{userId}/todos/{todoId}")
	public ResponseEntity<?> deleteTodo(@PathVariable String userId, @PathVariable int todoId) {
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
	    if (!Objects.equals(todo.getUserId(), userId)) {
	        // todo가 해당 유저에 속하지 않는 경우에 대한 예외 처리
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    todoRepository.deleteById(todoId);
	    
	    return ResponseEntity.noContent().build();
	}
	
	//Update Todo
	@PutMapping("/users/{userId}/todos/{todoId}")
	public ResponseEntity<?> updateTodo(@PathVariable String userId, @PathVariable int todoId, @Valid @RequestBody Todo updatedTodo) {
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
	    if (!Objects.equals(todo.getUserId(), userId)) {
	        
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    // update todo
	  //todo_id , user_id, task, finish, no_tag. share , todo_date
	    todo.setTask(updatedTodo.getTask());
	    todo.setNoTag(updatedTodo.getNoTag());
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

	/*
	3. Schedule : Find All /Create / Delete /Update Schedule
	*/

	//one User -> all schedules
	@GetMapping("/users/{id}/schedules")
	public List<Schedule> retrieveSchForUser(@PathVariable("id") String id) {
		try {
			Optional<User> user = userRepository.findById(id);

			if (user.isEmpty()) {
				throw new UserNotFoundException("User not found with id: " + id);
			}
			return user.get().getSchedules();
		} catch (Exception e) {
			throw new RuntimeException("Error retrieving schedules for user with id: " + id, e);
		}
	}


	//create schedules
	@PostMapping("/users/{id}/schedules")
	public ResponseEntity<Object> createSchForUser(@PathVariable String id, @Valid @RequestBody Schedule schedule) {

		Optional<User> user = userRepository.findById(id);

		System.out.println("Empty: " + user.isEmpty());

		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);

		schedule.setUser(user.get());

		Schedule savedSch = schRepository.save(schedule);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedSch.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	//delete Schedule
	@DeleteMapping("/users/{userId}/schedules/{scheduleId}")
	public ResponseEntity<?> deleteSch(@PathVariable String userId, @PathVariable int scheduleId) {
		//find user_id
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("id:" + userId);
		}
		//find to exist sche_id
		Optional<Schedule> schOptional = schRepository.findById(scheduleId);
		if (schOptional.isEmpty()) {
			throw new ScheduleNotFoundException("id:" + scheduleId);
		}

		Schedule schedule = schOptional.get();
		if (!Objects.equals(schedule.getId(), userId)) {
			// schedule가 해당 유저에 속하지 않는 경우에 대한 예외 처리
			throw new ScheduleNotFoundException("id:" + scheduleId);
		}

		schRepository.deleteById(scheduleId);

		return ResponseEntity.noContent().build();
	}

	//Update Schedules
	@PutMapping("/users/{userId}/schedules/{scheduleId}")
	public ResponseEntity<?> updateSch(@PathVariable String userId, @PathVariable int scheduleId,
									   @Valid @RequestBody Schedule updatedSch) {
		//Exception
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("id:" + userId);
		}

		Optional<Schedule> schOptional = schRepository.findById(scheduleId);
		if (schOptional.isEmpty()) {
			throw new ScheduleNotFoundException("id:" + scheduleId);
		}

		Schedule schedule = schOptional.get();
		if (!Objects.equals(schedule.getUser().getUserId(), userId)) {
			throw new ScheduleNotFoundException("id:" + scheduleId);
		}

		// update Schedule
		//Sche_id, user_id, color(dcdcdc), start_date , end_date, plan

		schedule.setColor(updatedSch.getColor());
		schedule.setStart_date(updatedSch.getStart_date());
		schedule.setEnd_date(updatedSch.getEnd_date());
		schedule.setPlan(updatedSch.getPlan());
		schRepository.save(schedule);
		return ResponseEntity.ok().build();
	}


	
}