package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Jwt.Blacklist;
import com.example.demo.Jwt.BlacklistRepository;
import com.example.demo.Jwt.JwtFunc;
import com.example.demo.domain.User;
import com.example.demo.dto.PasswordUpdateRequest;
import com.example.demo.dto.UserInfoResponse;
import com.example.demo.exception.InvalidJwtTokenException;
import com.example.demo.repository.ChallengerRepository;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {
    private UserRepository userRepository;

    private BlacklistRepository blacklistRepository;
//    private TodoRepository todoRepository;
//    private ScheduleRepository scheduleRepository;
//    private PerformRepository performRepository;
//    private FollowRepository followRepository;
//    private LikeRepository likeRepository;
//    private PostRepository postRepository;
//    private ChallengerRepository challengerRepository;
//    private DiaryRepository diaryRepository;

    public UserController(UserRepository userRepository, FollowRepository followRepository, LikeRepository likeRepository, PostRepository postRepository, ChallengerRepository challengerRepository, DiaryRepository diaryRepository, BlacklistRepository blacklistRepository) {
        this.userRepository = userRepository;
//        this.followRepository = followRepository;
//        this.likeRepository = likeRepository;
//        this.postRepository = postRepository;
//        this.challengerRepository = challengerRepository; 
//        this.diaryRepository = diaryRepository;
        this.blacklistRepository = blacklistRepository;
    }
    
//    @Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     * 전체 사용자 조회
     */
    // GET /users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    

    /*
     * 회원정보 조회 => 발급받은 토큰으로 수정해야 함(완)
     */
    // GET /user_info
    @GetMapping("/user_info")
    public EntityModel<UserInfoResponse> retrieveUser(@RequestHeader("Authorization") String token) {

        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);	//토큰 뜯어서 user_id 얻는다.

        checkBlacklist(token);                  //블랙리스트 검사

        Optional<User> userOptional = userRepository.findById(user_id);

        // 이메일, 이름, 레벨 정보만 반환
        //+ 계정 공개여부, 상태메시지 반환
        User user = userOptional.get();
        UserInfoResponse userInfoResponse = new UserInfoResponse(user.getEmail(), user.getUsername(), user.getLevel(), user.getAuth(), user.getUserMsg());

        EntityModel<UserInfoResponse> entityModel = EntityModel.of(userInfoResponse);

        return entityModel;
    }

    /*
     * 회원정보 수정
     */
    // PUT /user_info
    @PutMapping("/user_info")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token, @RequestBody User updatedUser){

        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);

        Optional<User> userOptional = userRepository.findById(user_id);
        User existingUser = userOptional.get();

        // 업데이트는 이메일과 이름만 가능
        // + 공개 여부와 상태메시지 수정가능
        //(바꿀 것이 있을 때만 수정해야 함. 아니면 null로 업데이트 될 수 있음)
        if(updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
        if(updatedUser.getUsername() != null) existingUser.setUsername(updatedUser.getUsername());
        if(updatedUser.getAuth() != -1) existingUser.setAuth(updatedUser.getAuth());
        if(updatedUser.getUserMsg() != null) existingUser.setUserMsg(updatedUser.getUserMsg());

        userRepository.save(existingUser); // 수정된 정보 저장

        return ResponseEntity.ok("Successfully modify!");
    }

    /*
     * 비밀번호 수정
     */
    @PutMapping("/modify_pw")
    public ResponseEntity<String> updatePW(@RequestHeader("Authorization") String token,
                                           @RequestBody PasswordUpdateRequest pwUpdateRequest) {
        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);

        Optional<User> userOptional = userRepository.findById(user_id);
        User existingUser = userOptional.get();

        // 현재 비밀번호 확인
        if (!existingUser.getPassword().equals(pwUpdateRequest.getCurrentPw())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect.");
        }

        // 새 비밀번호 업데이트
        existingUser.setPassword(pwUpdateRequest.getNewPw());
        userRepository.save(existingUser); // 수정된 정보 저장

        return ResponseEntity.status(HttpStatus.OK).body("Password Successfully modify!");
    }

    //블랙리스트
    public void checkBlacklist(String token) {
        blacklistRepository.deleteByDeleteAtBefore(new Date());
        Optional<Blacklist> blacklistOptional = blacklistRepository.findById(token);

        //블랙리스트에 있으면 유효하지 않은 토큰.
        if(blacklistOptional.isPresent()) throw new InvalidJwtTokenException("유효하지 않은 토큰입니다.");
    }

    public void registerBlacklist(String token) {
        LocalDate after10Days = LocalDate.now().plusDays(10);
        Date after10DaysDate = java.sql.Date.valueOf(after10Days);
        blacklistRepository.save(new Blacklist(token, after10DaysDate));
    }
    
}

