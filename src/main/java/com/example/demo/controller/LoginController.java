package com.example.demo.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Jwt.BlacklistRepository;
import com.example.demo.repository.ChallengerRepository;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class LoginController {

	private UserRepository userRepository;
	
	public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
   
    }

	@GetMapping({ "", "/" })
	public @ResponseBody String index() {
		return "인덱스 페이지입니다.";
	}
	
	@GetMapping("/log_in")
    public String showLoginPage() {
        return "login"; // 'login.mustache' 파일을 렌더링
    }
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}

//	@PostMapping("/joinProc")
//	public String joinProc(User user) {
//		System.out.println("회원가입 진행 : " + user);
//		user.setAuth(0);	//기본값 : 0(비공개)
//        user.setLevel(1);	//기본값 : 1(lv.)
//        
//		userRepository.save(user);
//		return "redirect:/";
//	}

}
