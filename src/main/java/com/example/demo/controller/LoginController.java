package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Jwt.Blacklist;
import com.example.demo.Jwt.BlacklistRepository;
import com.example.demo.Jwt.JwtFunc;
import com.example.demo.domain.User;
import com.example.demo.exception.InvalidJwtTokenException;
import com.example.demo.repository.ChallengerRepository;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class LoginController {

	private UserRepository userRepository;
	private BlacklistRepository blacklistRepository;
	private FollowRepository followRepository;
	private LikeRepository likeRepository;
	private PostRepository postRepository;
	private ChallengerRepository challengerRepository;
	private DiaryRepository diaryRepository;
	
	public LoginController(UserRepository userRepository, FollowRepository followRepository, LikeRepository likeRepository, PostRepository postRepository, ChallengerRepository challengerRepository, DiaryRepository diaryRepository, BlacklistRepository blacklistRepository) {
	      this.userRepository = userRepository;
	      this.followRepository = followRepository;
	      this.likeRepository = likeRepository;
	      this.postRepository = postRepository;
	      this.challengerRepository = challengerRepository;
	      this.diaryRepository = diaryRepository;
	      this.blacklistRepository = blacklistRepository;
  }
  

//	@GetMapping({ "", "/" })
//	public @ResponseBody String index() {
//		return "인덱스페이지";
//	}
//	
	/*
     * 회원가입
     */
	@GetMapping("/sign_in")
	public String getSigninPage() {
		return "join";
	}
	
	/*
     * 로그인
     */
	@GetMapping("/log_in")
    public String showLoginPage() {
        return "login"; // 'login.mustache' 파일을 렌더링
    }
	
	/*
     * 회원가입 처리
     */
    // POST /sign_up
    @PostMapping("/sign_up")
    public ResponseEntity<Void> createUser(@RequestBody User user) {

        user.setAuth(0);	//기본값 : 0(비공개)
        user.setLevel(1);	//기본값 : 1(lv.)
        // 비밀번호 암호화->추후 리펙토링
// 		String rawPassword = user.getPassword(); // 원래 비밀번호
// 		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 암호화
// 		user.setPassword(encPassword);
 		
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
  
    /*
     * 로그인 처리(토큰 발급 추가 완)
     */
    // POST /log_in
    @PostMapping("/log_in")
    public ResponseEntity<String> loginUser(@RequestBody User user) {

        String email = user.getEmail();
        String password = user.getPassword();

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password))
        {
            // 로그인 성공 시 JWT 토큰 생성하여 반환
            String token = Jwts.builder()
                    .setSubject(userOptional.get().getUserId())
                    .setExpiration(new Date(System.currentTimeMillis() + 60*100000)) // 100분, 10일 : 864000000
                    .signWith(SignatureAlgorithm.HS256, new JwtFunc().getSECRET_KEY()) // 서명
                    .compact();

            return ResponseEntity.ok(token);
        }

        // 아이디 없거나 비밀번호 틀림
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Unauthorized");
    }

    /*
     * 로그아웃(완)
     */
    // POST /log_out
    @PostMapping("/log_out")
    public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String token){

        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);

        //블랙리스트 등록
        registerBlacklist(token);

        return ResponseEntity.ok("Logout");
    }
	
	/*
     * 회원 탈퇴(유저와 관련된 정보 모두 삭제해야 함)(미완)
     */
    // DELETE /delete_account
    @DeleteMapping("/delete_account")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token){
        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);

        //블랙리스트 등록
        registerBlacklist(token);

        //DB에서 모든 정보 삭제
        userRepository.deleteById(user_id);
//        todoRepository.deleteByUserId(user_id);
//        scheduleRepository.deleteByUserId(user_id);
//        performRepository.deleteById(user_id);
        followRepository.deleteByFollowing(user_id);
        followRepository.deleteByFollowed(user_id);
//        followRepository.deleteByFollowed(user_id);

        likeRepository.deleteByUserId(user_id);
        postRepository.deleteByUserId(user_id);
        challengerRepository.deleteByUserId(user_id);
        diaryRepository.deleteByUserId(user_id);

        return ResponseEntity.ok("회원탈퇴 되셨습니다.");
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
