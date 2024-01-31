package com.example.winterproject.todoApplication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.winterproject.todoApplication.domain.Follow;
import com.example.winterproject.todoApplication.domain.FollowId;
import com.example.winterproject.todoApplication.domain.User;
import com.example.winterproject.todoApplication.repository.FollowRepository;
import com.example.winterproject.todoApplication.repository.UserRepository;
import com.example.winterproject.todoApplication.repository.dto.FollowingUserInfo;

@RestController
public class FollowResource {
	private FollowRepository followRepository;
	private UserRepository userRepository;
	
	public FollowResource(FollowRepository followRepository, UserRepository userRepository) {
		this.followRepository = followRepository;
		this.userRepository = userRepository;
	 }
	 
	/*
	 * 전체 조회
	 */
	// GET /follow
	@GetMapping("/follow")
	public List<Follow> retrieveAllUsers(){
		return followRepository.findAll();		
	}
	 
	/*
	 * 팔로우
	 */
	// POST /follow
	@PostMapping("/follow")
	public ResponseEntity<String> followUser(@RequestBody FollowId followId) {
		Optional<User> followerOptional = userRepository.findById(followId.getFollower());
		Optional<User> followingOptional = userRepository.findById(followId.getFollowing());
		
		// follwer랑 following 모두 존재하면 follow
		if (followerOptional.isPresent() && followingOptional.isPresent()) {
			Follow follow = new Follow();
		    follow.setFollowId(followId); 
		    // 복합키 각각 설정해야 함		    
		    follow.setFollowerUser(followerOptional.get());
		    follow.setFollowingUser(followingOptional.get());
		    followRepository.save(follow);
		    return ResponseEntity.ok("Follow."); 
		}
		return ResponseEntity.badRequest().build();
	   
	}
	
	/*
	 * 언팔로우
	 */
	// DELETE /cancel_follow/{follower}/{following}
	@DeleteMapping("/cancel_follow/{follower}/{following}")
	public ResponseEntity<String> cancelFollow(@PathVariable String follower, @PathVariable String following) {
	    FollowId followId = new FollowId();
	    followId.setFollower(follower);
	    followId.setFollowing(following);
	    
	    Optional<Follow> followOptional = followRepository.findById(followId);
	    
	    if (followOptional.isPresent()) {
	        Follow follow = followOptional.get();
	        followRepository.delete(follow); // 팔로우 관계 삭제
	        return ResponseEntity.ok("Unfollow."); // 언팔로우 성공
	    } else {
	    	return ResponseEntity.badRequest().build();
	    }
	}
	
	/*
	 *  팔로워 조회
	 */
	// GET /following/{userId}
	// userId가 팔로우하고 있는 유저 목록
	@GetMapping("/following/{userId}")
    public List<Follow> retrieveFollowing(@PathVariable String userId) {
        return followRepository.findByFollowIdFollower(userId);
    }
	
	/*
	 *  팔로잉 조회
	 */
	// GET /follower/{userId}
	// userId를 팔로우하는 유저들의 목록
	@GetMapping("/follower/{userId}")
    public List<Follow> retrieveFollower(@PathVariable String userId) {
        return followRepository.findByFollowIdFollowing(userId);
    }
    
    
}
