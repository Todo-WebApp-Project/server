package com.example.winterproject.todoApplication.domain;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity(name="users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
	
	
	@Id
	@Column(name="user_id")
	private String userId;
	
	private String email;
	
	private String password;
	
	private String name;
	
	private int level = 1; // default level
	
	@OneToMany(mappedBy = "followerUser")
    private List<Follow> followers;

    @OneToMany(mappedBy = "followingUser")
    private List<Follow> followings;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Todo> todo;
    
    @PrePersist
    public void generateUUID() {
        this.userId = UUID.randomUUID().toString();
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

	public List<Follow> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Follow> followers) {
		this.followers = followers;
	}

	public List<Follow> getFollowings() {
		return followings;
	}

	public void setFollowings(List<Follow> followings) {
		this.followings = followings;
	}

	public List<Todo> getTodo() {
		return todo;
	}

	public void setTodo(List<Todo> todo) {
		this.todo = todo;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", name=" + name + ", level="
				+ level + ", followers=" + followers + ", followings=" + followings + ", todo=" + todo + "]";
	}
	
	
	
}
