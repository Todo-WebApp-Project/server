package com.example.winterproject.todoApplication.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.winterproject.todoApplication.domain.Follow;
import com.example.winterproject.todoApplication.domain.FollowId;
import com.example.winterproject.todoApplication.domain.User;
import com.example.winterproject.todoApplication.repository.FollowRepository;
import com.example.winterproject.todoApplication.repository.UserRepository;

@RestController
public class FollowResource {
	private FollowRepository followRepository;
	private UserRepository userRepository;
	
	public FollowResource(FollowRepository followRepository, UserRepository userRepository) {
		this.followRepository = followRepository;
		this.userRepository = userRepository;
	 }
	 
	/*
	 * 전체 사용자 조회 (개발 시 테스트 확인용)
	 */
	// GET /follow
	@GetMapping("/follow")
	public List<Follow> retrieveAllUsers(){
		return followRepository.findAll();		
	}
	 
	/*
	 * 팔로우 => 토큰 필요
	 */
	// POST /follow
	@PostMapping("/follow")
	public ResponseEntity<String> followUser(@RequestBody FollowId followId) {
		Optional<User> followerOptional = userRepository.findById(followId.getFollowing());
		Optional<User> followingOptional = userRepository.findById(followId.getFollowed());
		
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
	 * 언팔로우 => 토큰 필요
	 */
	// DELETE /cancel_follow/{following}/{followed}
	@DeleteMapping("/cancel_follow/{following}/{followed}")
	public ResponseEntity<String> cancelFollow(@PathVariable String following, @PathVariable String followed) {
	    FollowId followId = new FollowId();
	    followId.setFollowing(following);
	    followId.setFollowed(followed);
	    
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
	// GET /followers?userId=72e73d4c50fd
	// 연예인A를 팔로우 하고 있는 사람 목록 반환(연예인 A의 팔로워들)
	@GetMapping("/followers")
	public Map<String, List<String>> retrieveFollowers(@RequestParam String userId) {
        List<String> followedUserNames = followRepository.findByFollowIdFollowed(userId).stream()
                .map(follow -> {
                    return follow.getFollowerUser().getName(); // userName으로 반환 
                })
                .collect(Collectors.toList());

        return Map.of("following", followedUserNames);
    }

	
	/*
	 *  팔로잉 조회
	 */
	// GET /following/?userId=72e73d4c50fd
	// 연예인A가 팔로우 하고 있는 사람의 목록 반환
	@GetMapping("/following")
	public Map<String, List<String>> retrieveFollowing(@RequestParam String userId) {
		List<String> followingUserNames = followRepository.findByFollowIdFollowing(userId).stream()
                .map(follow -> {
                    return follow.getFollowingUser().getName(); // userName으로 반환 
                })
                .collect(Collectors.toList());

        return Map.of("followed", followingUserNames);
	}

	/*
	 * userID에 대한 팔로잉 수와 팔로워 수 조회
	 */
    @GetMapping("/follow/count")
    public ResponseEntity<?> getFollowCounts(@RequestParam String userId) {
        long followingCount = followRepository.countByFollowIdFollowing(userId);
        long followerCount = followRepository.countByFollowIdFollowed(userId);

        Map<String, Long> counts = new HashMap<>();
        counts.put("followingCount", followingCount);
        counts.put("followerCount", followerCount);

        return ResponseEntity.ok(counts);
    }
}