package com.example.winterproject.todoApplication.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class LikeId implements Serializable {
	private Integer todo_id; 
    private String user_id;
	public Integer getTodo_id() {
		return todo_id;
	}
	public void setTodo_id(Integer todo_id) {
		this.todo_id = todo_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "LikeId [todo_id=" + todo_id + ", user_id=" + user_id + "]";
	}
    
    
    
	
}
