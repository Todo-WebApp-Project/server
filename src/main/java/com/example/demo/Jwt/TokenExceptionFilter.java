package com.example.demo.Jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.exception.InvalidJwtTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
            filterChain.doFilter(request, response);
        } catch (InvalidJwtTokenException  e) {
            //response.sendError(e.getMessage());
        	ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
		
	}
	
	

}
