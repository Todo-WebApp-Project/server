package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable());
		http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/log_in/", "/log_out/", "/sign_in/","/challenge/**").permitAll()
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/log_in") // 로그인 페이지 URL
            .loginProcessingUrl("/login") // 로그인 처리 URL
            .defaultSuccessUrl("/") // 로그인 성공 후 리디렉션할 URL
            .permitAll());
		
		return http.build();
		
	}
}
