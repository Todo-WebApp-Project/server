package com.example.winterproject.todoApplication.repository.dto;

public class GoogleUserDto {
	private String id;       // 구글 사용자의 고유 ID
    private String email;    // 구글 사용자의 이메일
    private String name;     // 구글 사용자의 이름

    // 기본 생성자
    public GoogleUserDto() {
    }
    // 모든 필드를 포함하는 생성자
	public GoogleUserDto(String id, String email, String name) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "GoogleUserDto [id=" + id + ", email=" + email + ", name=" + name + "]";
	}

    
    
}
