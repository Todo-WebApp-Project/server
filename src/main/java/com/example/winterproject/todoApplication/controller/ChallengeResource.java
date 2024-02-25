package com.example.winterproject.todoApplication.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.winterproject.todoApplication.domain.Challenge;
import com.example.winterproject.todoApplication.exception.UserNotFoundException;
import com.example.winterproject.todoApplication.repository.ChallengeRepository;

@RestController
public class ChallengeResource {
	
	private ChallengeRepository challengeRepository;
	
	 public ChallengeResource(ChallengeRepository challengeRepository) {
		 this.challengeRepository = challengeRepository;
	 }
	 
	 /*
	  * 전체 챌린지 조회
	  */
	 // GET /users 
	@GetMapping("/challenges")
	public List<Challenge> getAllChallenges(){
		return challengeRepository.findAll();
		 //List<Challenge> challenges = challengeService.findAll();
	     //   return ResponseEntity.ok(challenges);
	}
	
	/*
	 * 챌린지 생성
	 */
	// POST /challenges
	@PostMapping("/challenges")
	public ResponseEntity<Map<String, Object>> createChallenge(@RequestBody Challenge challenge) {
	    Challenge savedChallenge = challengeRepository.save(challenge);
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "The challenge was successfully created.");
	    response.put("challenge", savedChallenge);

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/*
	 * 챌린지 수정
	 */
	// PUT /challenges/{noTag}
	@PutMapping("/challenges/{noTag}")
	public ResponseEntity<String> updateChallenge(@PathVariable Long noTag, @RequestBody Challenge challengeDetails){
		
		Optional<Challenge> challengeOptional = challengeRepository.findById(noTag);

	    if (challengeOptional.isEmpty()) {
	        throw new UserNotFoundException("id:" + noTag);
	    }

	    Challenge existingChallenge = challengeOptional.get();
	    // 업데이트는 tagName, tagDesc, mainImg만 가능
	    existingChallenge.setTagName(challengeDetails.getTagName());
	    existingChallenge.setTagDesc(challengeDetails.getTagDesc());
	    existingChallenge.setMainImg(challengeDetails.getMainImg());
	    
	    challengeRepository.save(existingChallenge); // 수정된 정보 저장
	    
	    return ResponseEntity.status(200).body("The challenge was successfully modified.");
	}
	
	/*
	 * 챌린지 삭제
	 */
	// DELETE /challenges/{noTag}
	@DeleteMapping("/challenges/{noTag}")
	public ResponseEntity<String> deleteUser(@PathVariable Long noTag){
		challengeRepository.deleteById(noTag);
	    return ResponseEntity.status(200).body("The challenge was successfully deleted.");
	}
	
}
