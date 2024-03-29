package com.example.demo.controller;



import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.Jwt.JwtFunc;
import com.example.demo.repository.*;
import com.example.demo.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

	JwtFunc jwt = new JwtFunc();

	public TodoAndScheduleController(UserRepository userRepository, TodoRepository todoRepository, ScheduleRepository schRepository ) {
		this.userRepository = userRepository;
		this.todoRepository = todoRepository;
		this.schRepository = schRepository;
	}

	// (2)Todo
	//one User -> all posting
	@GetMapping("/users/todos")
	public List<Todo> retrievePostsForUser(@RequestHeader("Authorization") String token) {

		JwtFunc jwt = new JwtFunc();
		Optional<User> user = userRepository.findById(jwt.unpackJWT(token));

		if(user.isEmpty())
			throw new UserNotFoundException("id Not Found");
		
		return user.get().getTodos();
	}
	// delete user
	@DeleteMapping("/users")
	public void deleteUser(@RequestHeader("Authorization") String token) {
		userRepository.deleteById(jwt.unpackJWT(token));
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
	@PostMapping("/users/todos")
	public ResponseEntity<Object> createTodoForUser(@RequestHeader("Authorization") String token, @Valid @RequestBody Todo todo) {

		Optional<User> user = userRepository.findById(jwt.unpackJWT(token));

		if(user.isEmpty())
			throw new UserNotFoundException("id is not found");

		Todo savedTodo = todoRepository.save(todo);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/"+user.get().getUserId())
				.buildAndExpand(savedTodo.getUserId())
				.toUri();   

		return ResponseEntity.created(location).build();
	}
	
	//delete Todo
	@DeleteMapping("/users/todos/{todoId}")
	public ResponseEntity<?> deleteTodo(@RequestHeader("Authorization") String token, @PathVariable int todoId) {

		JwtFunc jwt = new JwtFunc();
		Optional<User> userOptional = userRepository.findById(jwt.unpackJWT(token));

		if (userOptional.isEmpty()) {
	        throw new UserNotFoundException("id is not found");
	    }
	    //find to exist todo_id
	    Optional<Todo> todoOptional = todoRepository.findById(todoId);
	    if (todoOptional.isEmpty()) {
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    Todo todo = todoOptional.get();
	    if (!Objects.equals(todo.getUserId(), userOptional.get().getUserId())) {
	        // todo가 해당 유저에 속하지 않는 경우에 대한 예외 처리
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    todoRepository.deleteById(todoId);
	    
	    return ResponseEntity.noContent().build();
	}
	
	//Update Todo
	@PutMapping("/users/todos/{todoId}")
	public ResponseEntity<?> updateTodo(@RequestHeader("Authorization") String token, @PathVariable int todoId, @Valid @RequestBody Todo updatedTodo) {

		JwtFunc jwt = new JwtFunc();
		Optional<User> userOptional = userRepository.findById(jwt.unpackJWT(token));

	    if (userOptional.isEmpty()) {
	        throw new UserNotFoundException("id is not found");
	    }
	  
	    Optional<Todo> todoOptional = todoRepository.findById(todoId);
	    if (todoOptional.isEmpty()) {
	        throw new TodoNotFoundException("id:" + todoId);
	    }
	    
	    Todo todo = todoOptional.get();
	    if (!Objects.equals(todo.getUserId(), userOptional.get().getUserId())) {
	        
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
	@PatchMapping("/users/todos/{todoId}/todoChecking")
	public ResponseEntity<?> togglePostFinished(@RequestHeader("Authorization") String token,@PathVariable int todoId) {
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
	@GetMapping("/users/schedules")
	public List<Schedule> retrieveSchForUser(@RequestHeader("Authorization") String token) {
		Optional<User> user = userRepository.findById(jwt.unpackJWT(token));

		try {
			if (user.isEmpty()) {
				throw new UserNotFoundException("User not found");
			}
			return user.get().getSchedules();
		} catch (Exception e) {
			throw new RuntimeException("Error retrieving schedules for user with id: " + user.get().getUserId(), e);
		}
	}


	//create schedules
	@PostMapping("/users/schedules")
	public ResponseEntity<Object> createSchForUser( @RequestHeader("Authorization") String token,@Valid @RequestBody Schedule schedule) {

		Optional<User> user = userRepository.findById(jwt.unpackJWT(token));

		System.out.println("Empty: " + user.isEmpty());

		if(user.isEmpty())
			throw new UserNotFoundException("id is not found");

		schedule.setUser(user.get());

		Schedule savedSch = schRepository.save(schedule);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/"+user.get().getUserId())
				.buildAndExpand(savedSch.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	//delete Schedule
	@DeleteMapping("/users/schedules/{scheduleId}")
	public ResponseEntity<?> deleteSch(@RequestHeader("Authorization") String token,@PathVariable int scheduleId) {
		//find user_id
		Optional<User> userOptional = userRepository.findById(jwt.unpackJWT(token));
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("id is not found");
		}
		//find to exist sche_id
		Optional<Schedule> schOptional = schRepository.findById(scheduleId);
		if (schOptional.isEmpty()) {
			throw new ScheduleNotFoundException("id:" + scheduleId);
		}

		Schedule schedule = schOptional.get();
		if (!Objects.equals(schedule.getId().toString(), userOptional.get().getUserId())) {
			// schedule가 해당 유저에 속하지 않는 경우에 대한 예외 처리
			throw new ScheduleNotFoundException("id:" + scheduleId);
		}

		schRepository.deleteById(scheduleId);

		return ResponseEntity.noContent().build();
	}

	//Update Schedules
	@PutMapping("/users/schedules/{scheduleId}")
	public ResponseEntity<?> updateSch(@RequestHeader("Authorization") String token, @PathVariable int scheduleId,
									   @Valid @RequestBody Schedule updatedSch) {
		//Exception
		Optional<User> userOptional = userRepository.findById(jwt.unpackJWT(token));
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("id is not found");
		}

		Optional<Schedule> schOptional = schRepository.findById(scheduleId);
		if (schOptional.isEmpty()) {
			throw new ScheduleNotFoundException("id:" + scheduleId);
		}

		Schedule schedule = schOptional.get();
		if (!Objects.equals(schedule.getUser().getUserId(), userOptional.get().getUserId())) {
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