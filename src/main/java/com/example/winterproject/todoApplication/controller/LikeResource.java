package com.example.winterproject.todoApplication.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.winterproject.todoApplication.domain.Like;
import com.example.winterproject.todoApplication.domain.LikeId;
import com.example.winterproject.todoApplication.repository.LikeRepository;



@RestController
public class LikeResource {
	
	private LikeRepository likeRepository;
	
	public LikeResource(LikeRepository likeRepository) {
		 this.likeRepository = likeRepository;
	}
	
	/*
	 * 전체 조회
	 */
	// GET /like
	@GetMapping("/like")
	public List<Like> retrieveLike() {
		return likeRepository.findAll();
	}
	
	/*
	 * 좋아요
	 */
    // POST /like
    @PostMapping("/like")
    public ResponseEntity<String> likeTodo(@RequestBody LikeId likeId) {
	    Like like = new Like();
	    like.setLikeId(likeId);
	    likeRepository.save(like);
	
	    return ResponseEntity.ok("Liked.");
    }
		
    /*
     * 좋아요 취소
     */
    // DELETE /cancel_like?userId=72e73d4c50fdj&postId=1
    @DeleteMapping("/cancel_like")
    public ResponseEntity<String> cancelLike(@RequestParam("userId") String userId, @RequestParam("todoId") Integer todoId) {
    	LikeId likeId = new LikeId();
    	likeId.setUser_id(userId);
    	likeId.setTodo_id(todoId);
        
        Optional<Like> likeOptional = likeRepository.findById(likeId);

        if (likeOptional.isPresent()) {
        	Like like = likeOptional.get();
            likeRepository.delete(like);
            return ResponseEntity.ok("Liked Canceled.");
        } else {
            return ResponseEntity.badRequest().body("Like not found.");
        }
    }
    
    /*
     * 특정 게시물의 좋아요 수 조회
     */
    /*
     * @GetMapping("/like/count")
     * 
     */
    
}
