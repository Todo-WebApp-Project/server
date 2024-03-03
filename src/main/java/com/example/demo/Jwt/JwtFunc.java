package com.example.demo.Jwt;

import com.example.demo.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtFunc {

    private final String SECRET_KEY = "MySecretKey";		//토큰 비밀키

    public String unpackJWT(String token) {
        Claims claims = null;

        try {
            // 토큰 뜯는다.
            // 만약 토큰이 만료되거나 틀리면 에러발생.
            claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace("Bearer ", "")).getBody();

        } catch(Exception e) {
            throw new InvalidJwtTokenException("유효하지 않은 토큰입니다.");
        }

        return claims.getSubject();     //user_id 반환
    }

    public String getSECRET_KEY() {
        return SECRET_KEY;
    }
}

