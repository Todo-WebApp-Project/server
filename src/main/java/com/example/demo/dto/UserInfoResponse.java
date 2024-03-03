package com.example.demo.dto;

// User가 회원정보 조회 시, 전체 정보가 아닌 이메일, 이름, 레벨, 상태메시지 볼 수 있도록 하는 DTO
public class UserInfoResponse {
    private String email;
    private String username;
    private int level;
    private int auth;
    private String userMsg;

    public UserInfoResponse(String email, String username, int level, int auth, String userMsg) {
        this.email = email;
        this.username = username;
        this.level = level;
        this.auth = auth;
        this.userMsg = userMsg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }
}
