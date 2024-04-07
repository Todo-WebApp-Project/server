//package com.example.demo.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.example.demo.Jwt.JwtFunc;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//import com.example.demo.repository.*;
//import com.example.demo.domain.*;
//import com.example.demo.exception.UserNotFoundException;
//import com.example.demo.repository.PerformanceRepository;
//
//import jakarta.transaction.Transactional;
//
//@RestController
//public class PerformLevelingController {
//	
//	private UserRepository  userRepository;
//	private PerformanceRepository performRepository;
//
//	JwtFunc jwt = new JwtFunc();
//
//	public PerformLevelingController(UserRepository userRepository, PerformanceRepository performRepository) {
//		super();
//		this.userRepository = userRepository;
//		this.performRepository = performRepository;
//	}
//
//	/*4. Performance->User.Level*/
//    // 매일 00시 마다 level 작업
//	// @Get은 시험용
//    @Scheduled(cron = "0 0 0 * * ?")
//    @Transactional
//    @GetMapping("/users/updateLv")
//	public void performLeveling(@RequestHeader("Authorization") String token) {
//    	//User.user_id 을 조회 {select user_id from USER}
//		List<String> userIds = userRepository.findAllUserIds();
//
//	       for (String userId : userIds) {
//	            // 각 user_id에 대해 finish=1인 performance 레코드 수를 조회.
//	           int finishCount = performRepository.countByUserIdAndFinish(userId);
//	            // leveling 계산 조건 =일별 Todo 완료한 횟수/100 +1
//	           int newLevel = finishCount / 100 +1;
//
//	            // user_id 에 대한 User 
//	           User user = userRepository.findById(userId).orElse(null);
//
//	            // User 엔티티가 존재하고, 기존 level과 새로운 level이 다를 경우에만 수정.
//	           if (user != null && user.getLevel() != newLevel) {
//	               user.setLevel(newLevel);
//	               userRepository.save(user);
//	           }
//	       }
//    }
//    
//    //사용자 레벨 조회(필요 여부를 모르겠음)
//    @GetMapping("/users/levelChecking")
//    public int getUserLevel(@RequestHeader("Authorization") String token) {
//        Optional<User> userOptional = userRepository.findById(jwt.unpackJWT(token));
//
//		if(userOptional.isEmpty())
//			throw new UserNotFoundException("User with id " + userOptional.get().getUserId() + " not found");
//		else {
//			
//            return userOptional.get().getLevel();
//		}
//    }
//}
