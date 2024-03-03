package org.example.test1.dto;

// 비밀번호 수정 시, 현재 비밀번호와 새로운 비밀번호 정보를 받기 위한 DTO
public class PasswordUpdateRequest {
    private String currentPw;
    private String newPw;

    public PasswordUpdateRequest(String currentPw, String newPw) {
        this.currentPw = currentPw;
        this.newPw = newPw;
    }
    public String getCurrentPw() {
        return currentPw;
    }
    public void setCurrentPw(String currentPw) {
        this.currentPw = currentPw;
    }
    public String getNewPw() {
        return newPw;
    }
    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }
}

