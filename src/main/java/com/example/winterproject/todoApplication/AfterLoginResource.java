package com.example.winterproject.todoApplication;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AfterLoginResource {
	@GetMapping("/") // root로 매핑
	public String helloworld( Authentication authentication) {
		System.out.println(authentication);
		System.out.println(authentication.getPrincipal());
		return "Success Google Login";
	}
}
