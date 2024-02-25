package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

//todo_id , user_id, task, finish, no_tag. share , todo_date
@Entity
public class Todo {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
		
	private String task;
		
	private int finish;
	
	//comming soon to make table of Tag
	@ColumnDefault("0")
	private String no_tag; 
	
	private int share;
	
	@JsonProperty("todo_date")
	private LocalDate todoDate;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public String getNo_tag() {
		return no_tag;
	}

	public void setNo_tag(String no_tag) {
		this.no_tag = no_tag;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public LocalDate getTodoDate() {
		return todoDate;
	}

	public void setTodoDate(LocalDate todoDate) {
		this.todoDate = todoDate;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", user=" + user + ", task=" + task + ", finish=" + finish + ", no_tag=" + no_tag
				+ ", share=" + share + ", todoDate=" + todoDate + "]";
	}


	
	




	

}