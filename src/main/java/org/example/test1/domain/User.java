package org.example.test1.domain;

import java.util.UUID;
import jakarta.persistence.*;

@Entity(name="users")
public class User {

    @Id
    @Column(name="user_id")
    private String userId;

    private String email;

    private String password;

    private String username;

    private int level = -1;     //(**업데이트 할지 말지 확인해야 하므로 -1)

    private int auth = -1;	    //비공개 계정(**업데이트 할지 말지 확인해야 하므로 -1)

    @Column(name="user_msg")
    private String userMsg;		//상태메시지(초기화 안하면 자동으로 null 들어감)

    @PrePersist                 //DB에 저장되기 전에 실행되도록 하는 어노테이션.
    public void generateUUID() {
        this.userId = UUID.randomUUID().toString();     //36자
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", level=" + level +
                ", auth=" + auth +
                ", userMsg='" + userMsg + '\'' +
                '}';
    }
}

