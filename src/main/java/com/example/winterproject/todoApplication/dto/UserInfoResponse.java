package com.example.winterproject.todoApplication.dto;

public class UserInfoResponse {
    private String email;
    private String name;
    private int level;

    public UserInfoResponse() {
    }

    public UserInfoResponse(String email, String name, int level) {
        this.email = email;
        this.name = name;
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
