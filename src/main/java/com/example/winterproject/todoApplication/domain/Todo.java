package com.example.winterproject.todoApplication.domain;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@EntityScan
public class Todo {

	@Id
	@Column(name="todo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long todoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	@JsonIgnore	
	private User user;
	
    @Column(name="todo_date")
	private LocalDate todoDate;
	
	private String task;
	
	private Integer finish;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_id", nullable=false)
	@JsonIgnore
	private Post post;

	
	public Long getTodoId() {
		return todoId;
	}

	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getTodoDate() {
		return todoDate;
	}

	public void setTodoDate(LocalDate todoDate) {
		this.todoDate = todoDate;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Integer getFinish() {
		return finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", user=" + user + ", todoDate=" + todoDate + ", task=" + task + ", finish="
				+ finish + ", post=" + post + "]";
	}

	
}
