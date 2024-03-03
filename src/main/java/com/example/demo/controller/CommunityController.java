package com.example.demo.controller;

import com.example.demo.Jwt.JwtFunc;
import com.example.demo.domain.Follow;
import com.example.demo.domain.Like;
import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import com.example.demo.domain.ck.FollowId;
import com.example.demo.domain.ck.LikeId;
import com.example.demo.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CommunityController {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private FollowRepository followRepository;
    private LikeRepository likeRepository;

    public CommunityController(PostRepository postRepository, UserRepository userRepository, FollowRepository followRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.likeRepository = likeRepository;
    }

    //1-1. post 조회()
    //2-1. 팔로우 하기(O)
    //2-2. 팔로우 취소(O)
    //2-3. 팔로워 조회(A를 팔로우 하는 사람들 목록 조회)(O)
    //2-4. 팔로잉 조회(A가 팔로우 하는 사람들 목록 조회)(O)
    //3-1. 좋아요 하기(O)
    //3-2. 좋아요 취소(O)

    //****** 1-1. post 조회 ******


    //****** 2-1. 팔로우 하기 ******
    @PatchMapping("/follow")
    public ResponseEntity<String> follow(@RequestHeader("Authorization") String token,
                                             @RequestParam String followedId) {

        JwtFunc jwt = new JwtFunc();
        String userId = jwt.unpackJWT(token);

        Optional<User> followingOptional = userRepository.findById(userId);
        Optional<User> followedOptional = userRepository.findById(followedId);

//        System.out.println(followingOptional.get().getUserId());
//        System.out.println(followedOptional.get().getUserId());

        // follwer랑 following 모두 존재하면 follow
        if (followingOptional.isPresent() && followedOptional.isPresent()) {
            followRepository.save(new Follow(userId, followedId));
            return ResponseEntity.ok("Follow.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 조회불가");
    }

    //****** 2-2. 팔로우 취소 ******
    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestHeader("Authorization") String token,
                                             @RequestParam String followedId) {
        JwtFunc jwt = new JwtFunc();
        String userId = jwt.unpackJWT(token);

        followRepository.deleteById( new FollowId(userId, followedId) );

        return ResponseEntity.status(HttpStatus.OK).body("Unfollow.");
    }

    //****** 2-3. 팔로워 조회(A를 팔로우 하는 사람들 목록 조회) ******
    @GetMapping("/followers")
    public List<String> retrieveFollowers(@RequestParam String followed) {
        return followRepository.findFollowingByFollowed(followed).stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());
    }

    //****** 2-4. 팔로잉 조회(A가 팔로우 하는 사람들 목록 조회) ******
    @GetMapping("/followings")
    public List<String> retrieveFollowings(@RequestParam String following) {
        return followRepository.findFollowedByFollowing(following).stream()
                .map(Follow::getFollowed)
                .collect(Collectors.toList());
    }

    //****** 3-1. 좋아요 하기 ******
    @PatchMapping("/like")
    public ResponseEntity<String> like(@RequestHeader("Authorization") String token,
                                         @RequestParam Long postId) {

        JwtFunc jwt = new JwtFunc();
        String userId = jwt.unpackJWT(token);

        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Post> postOptional = postRepository.findById(postId);

        // user와 post 모두 존재하면 like
        if (userOptional.isPresent() && postOptional.isPresent()) {
            likeRepository.save(new Like(userId, postId));
            return ResponseEntity.ok("Like.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("post id 조회불가.");
    }


    //****** 3-2. 좋아요 취소 ******
    @DeleteMapping("/unlike")
    public ResponseEntity<String> unlike(@RequestHeader("Authorization") String token,
                                           @RequestParam Long postId) {
        JwtFunc jwt = new JwtFunc();
        String userId = jwt.unpackJWT(token);

        likeRepository.deleteById( new LikeId(userId, postId) );

        return ResponseEntity.status(HttpStatus.OK).body("Unlike.");
    }

}
