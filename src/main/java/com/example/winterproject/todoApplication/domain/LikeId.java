package com.example.winterproject.todoApplication.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class LikeId implements Serializable {
	private Long post_id; 
    private String user_id;
	public Long getPost_id() {
		return post_id;
	}
	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "LikeId [post_id=" + post_id + ", user_id=" + user_id + "]";
	}
       
	
}
