package org.example.test1.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.test1.Jwt.JwtFunc;
import org.example.test1.domain.Follow;
import org.example.test1.domain.Like;
import org.example.test1.domain.Post;
import org.example.test1.domain.Todo;
import org.example.test1.domain.User;
import org.example.test1.domain.ck.FollowId;
import org.example.test1.domain.ck.LikeId;
import org.example.test1.repository.FollowRepository;
import org.example.test1.repository.LikeRepository;
import org.example.test1.repository.PostRepository;
import org.example.test1.repository.TodoRepository;
import org.example.test1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityController {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private FollowRepository followRepository;
    private LikeRepository likeRepository;
    private TodoRepository todoRepository;

    public CommunityController(PostRepository postRepository, UserRepository userRepository, FollowRepository followRepository, LikeRepository likeRepository, TodoRepository todoRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.likeRepository = likeRepository;
        this.todoRepository = todoRepository;
    }

    //1-1. post 조회(O)
    //2-1. 팔로우 하기(O)
    //2-2. 팔로우 취소(O)
    //2-3. 팔로워 조회(A를 팔로우 하는 사람들 목록 조회)(O)
    //2-4. 팔로잉 조회(A가 팔로우 하는 사람들 목록 조회)(O)
    //3-1. 좋아요 하기(O)
    //3-2. 좋아요 취소(O)

    //****** 1-1. post 조회 ******
    @GetMapping("/get_posts")
    public ResponseEntity<List<Map<String,Object>>> getPosts(){
    	// 모든 사용자를 조회하여 공개 계정만 필터링한다.
        List<User> publicUsers = userRepository.findAll().stream()
            .filter(user -> user.getAuth() == 1) // auth가 0인, 즉 공개 계정만
            .collect(Collectors.toList()); //
        
        List<Map<String, Object>> postsWithTodos = new ArrayList<>(); // 최종적으로  반할 포스트와 투두 정보를 담을 리스트
        
     // 공개 계정에 대해 반복하여 각 사용자의 포스트와 관련 투두 정보를 가져옴
        for (User user : publicUsers) {
            List<Post> userPosts = postRepository.findByUserId(user.getUserId()); 
            for (Post post : userPosts) {
                Map<String, Object> postWithTodos = new HashMap<>();
                // 포스트의 ID, 사용자 이름, 그리고 포스트 날짜를 맵에 추가
                postWithTodos.put("postId", post.getPostId());
                postWithTodos.put("userName", user.getUsername());
                postWithTodos.put("todoDate", post.getPostDate());
                
             // 해당 포스트와 관련된 투두 항목들을 조회한다. 여기서 share가 1인 Todo만 필터링
                List<Todo> todos = todoRepository.findByUserIdAndTodoDate(user.getUserId(), post.getPostDate())
                    .stream()
                    .filter(todo -> todo.getShare() == 1) // 공개된 Todo만 필터링
                    .collect(Collectors.toList());
                
                // 조회된 투두 항목들을 맵에 tasks키 추가해서 할 일이랑 완료여부를 넣기
                postWithTodos.put("tasks", todos.stream().map(todo -> Map.of(
                        "task", todo.getTask(),
                        "finish", todo.getFinish()
                )).collect(Collectors.toList()));
                
                
                // 최종적으로 구성된 포스트와 투두 정보를 반환 리스트에 추가
                postsWithTodos.add(postWithTodos);
            }
        }
        
        return ResponseEntity.ok(postsWithTodos);
    }
    

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
