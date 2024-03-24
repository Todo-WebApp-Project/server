package com.example.demo.controller;

import com.example.demo.Jwt.Blacklist;
import com.example.demo.dto.UserInfoResponse;
import com.example.demo.exception.InvalidJwtTokenException;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.example.demo.Jwt.BlacklistRepository;
import com.example.demo.Jwt.JwtFunc;
import com.example.demo.domain.User;
import com.example.demo.dto.PasswordUpdateRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserRepository userRepository;

    private BlacklistRepository blacklistRepository;
//    private TodoRepository todoRepository;
//    private ScheduleRepository scheduleRepository;
//    private PerformRepository performRepository;
    private FollowRepository followRepository;
    private LikeRepository likeRepository;
    private PostRepository postRepository;
    private ChallengerRepository challengerRepository;
    private DiaryRepository diaryRepository;

    public UserController(UserRepository userRepository, FollowRepository followRepository, LikeRepository likeRepository, PostRepository postRepository, ChallengerRepository challengerRepository, DiaryRepository diaryRepository, BlacklistRepository blacklistRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.challengerRepository = challengerRepository;
        this.diaryRepository = diaryRepository;
        this.blacklistRepository = blacklistRepository;
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
    public ResponseEntity<Void> createUser(@RequestBody User user) {

        user.setAuth(0);	//기본값 : 0(비공개)
        user.setLevel(1);	//기본값 : 1(lv.)

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     * 로그인(토큰 발급 추가 완)
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

//    /*
//     * 구글 회원가입
//     */
//	/*@PostMapping("/googleLogin")
//	public ResponseEntity<String> loginUserWithGoogle(GoogleUserDto googleUserDto, HttpSession session) {
//		String email = googleUserDto.getEmail();
//		Optional<User> userOptional = userRepository.findByEmail(email);
//
//		// DB에 이미 존재한다면
//		if(userOptional.isPresent()) {
//			User existingUser = userOptional.get();
//			session.setAttribute("user", existingUser);
//			return ResponseEntity.ok("Existing Google user logged in successfully.");
//		}
//		// DB에 존재하지 않다면
//		else {
//			// 새로운 User 정보 저장
//			User newUser = new User();
//			newUser.setUserId(googleUserDto.getEmail());
//			newUser.setEmail(googleUserDto.getEmail());
//			newUser.setName(googleUserDto.getName());
//			newUser.setLevel(1);
//			// 비밀번호는 설정x
//
//			// 저장
//			userRepository.save(newUser);
//			session.setAttribute("user", newUser);
//			return ResponseEntity.ok().body("New Google user created and logged in successfully.");
//		}
//
//	}*/

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

