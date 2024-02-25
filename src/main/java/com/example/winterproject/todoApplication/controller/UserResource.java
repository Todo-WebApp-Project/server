package com.example.winterproject.todoApplication.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.winterproject.todoApplication.domain.User;
import com.example.winterproject.todoApplication.dto.UserInfoResponse;
import com.example.winterproject.todoApplication.exception.UserNotFoundException;
import com.example.winterproject.todoApplication.repository.UserRepository;
import com.example.winterproject.todoApplication.repository.dto.GoogleUserDto;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	private UserRepository userRepository;
	
	 public UserResource(UserRepository userRepository) {
		 this.userRepository = userRepository;
	 }
	 
	 /*
	  * 전체 사용자 조회
	  */
	 // GET /users 
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();		
	}
	
	/*
	 * 회원가입
	 */
	// POST /sign_up
	@PostMapping("/sign_up")
	public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		// /users/{id} => 즉, user.getId()
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedUser.getUserId())
							.toUri();
		return ResponseEntity.created(location).build();
	}
	
	/*
	 * 로그인
	 */
	// POST /log_in
	@PostMapping("/log_in")
	public ResponseEntity<String> loginUser(@RequestBody User user){
		String userId = user.getUserId();
		String password = user.getPassword();
		
		Optional<User> userOptional = userRepository.findById(userId);
		
		if (userOptional.isPresent()) {
	        User existingUser = userOptional.get();
	        if (existingUser.getPassword().equals(password)) {
	            // 로그인 성공
	            return ResponseEntity.ok("Log in!");
	        }
	    }

	    // 아이디 없거나 비밀번호 틀림
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Unauthorized");
		
	}
	
	/*
	 * 로그아웃
	 */
	// POST /log_out
	@PostMapping("/log_out")
	public ResponseEntity<String> logoutUser(@RequestBody User user){
		// 로그아웃 세션 또는 토큰 무효화
		// 이에 대한 코드 구현이 아직 안 되었으므로 BODY에 아래와 같이 입력
		// BODY: {}
		return ResponseEntity.ok("Logout");
	}
	
	/*
	 * 회원정보 조회
	 */
	// GET /user_info/{user_id}
	@GetMapping("/user_info/{user_id}")
	public EntityModel<UserInfoResponse> retrieveUser(@PathVariable String user_id){
		Optional<User> userOptional = userRepository.findById(user_id);
		
		if(userOptional.isEmpty())
			throw new UserNotFoundException("id:"+user_id);
		
		// 이메일, 이름, 레벨 정보만 반환
		User user = userOptional.get();
	    UserInfoResponse userInfoResponse = new UserInfoResponse(user.getEmail(), user.getName(), user.getLevel());

	    EntityModel<UserInfoResponse> entityModel = EntityModel.of(userInfoResponse);
	    
	    return entityModel;
	}


	/*
	 * 회원정보 수정
	 */
	// PUT /user_info/{user_id}
	@PutMapping("/user_info/{user_id}")
	public ResponseEntity<String> updateUser(@PathVariable String user_id, @RequestBody User updatedUser){
		
		Optional<User> userOptional = userRepository.findById(user_id);

	    if (userOptional.isEmpty()) {
	        throw new UserNotFoundException("id:" + user_id);
	    }

	    User existingUser = userOptional.get();
	    // 업데이트는 이메일과 이름만 가능
	    existingUser.setEmail(updatedUser.getEmail());
	    existingUser.setName(updatedUser.getName());

	    
	    userRepository.save(existingUser); // 수정된 정보 저장
	    
		return ResponseEntity.ok("Successfully modify!");
	}
	
	/*
	 * 회원 탈퇴
	 */
	// DELETE /delete_account/{user_id}
	@DeleteMapping("/delete_account/{user_id}")
	public ResponseEntity<String> deleteUser(@PathVariable String user_id){
		userRepository.deleteById(user_id);
	    return ResponseEntity.ok("delete account");
	}
	
	/*
	 * 구글 회원가입
	 */
	/*@PostMapping("/googleLogin")
	public ResponseEntity<String> loginUserWithGoogle(GoogleUserDto googleUserDto, HttpSession session) {
		String email = googleUserDto.getEmail();
		Optional<User> userOptional = userRepository.findByEmail(email);
		
		// DB에 이미 존재한다면
		if(userOptional.isPresent()) {
			User existingUser = userOptional.get();
			session.setAttribute("user", existingUser);
			return ResponseEntity.ok("Existing Google user logged in successfully.");
		}
		// DB에 존재하지 않다면
		else {
			// 새로운 User 정보 저장
			User newUser = new User();
			newUser.setUserId(googleUserDto.getEmail());
			newUser.setEmail(googleUserDto.getEmail());
			newUser.setName(googleUserDto.getName());
			newUser.setLevel(1);
			// 비밀번호는 설정x
			
			// 저장
			userRepository.save(newUser);
			session.setAttribute("user", newUser);
			return ResponseEntity.ok().body("New Google user created and logged in successfully.");
		}

	}*/
}
