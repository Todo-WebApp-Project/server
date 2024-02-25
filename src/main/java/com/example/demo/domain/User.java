package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;


@Entity(name="user_details")
public class User {
	
	protected User() {
		
	}
	// user_id, email, password, username, level, auth, status_msg
	@Id
	@GeneratedValue
	private String id;
	
	private String email;
	
	private String password;
	
	@Size(min=2, message = "Name should have atleast 2 characters")
	//@JsonProperty("user_name")
	private String username;
	
	@ColumnDefault("1")
	private Integer level ;
	
	@ColumnDefault("0")
	private Integer auth;
	
//	@Size()
	private String status_msg;
	
	
	//@Past(message = "Birth Date should be in the past")
	//@JsonProperty("birth_date")
//	private LocalDate birthDate;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Todo> todos;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Schedule> schedules;
	
	
	/// new performance
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Performance> performances;

	


	public User(String id, String email, String password,
			@Size(min = 2, message = "Name should have atleast 2 characters") String username, Integer level,
			Integer auth, String status_msg, List<Todo> todos, List<Schedule> schedules,
			List<com.example.demo.domain.Performance> performances) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.level = level;
		this.auth = auth;
		this.status_msg = status_msg;
		this.todos = todos;
		this.schedules = schedules;
		this.performances = performances;
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


	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public Integer getAuth() {
		return auth;
	}


	public void setAuth(Integer auth) {
		this.auth = auth;
	}


	public String getStatus_msg() {
		return status_msg;
	}


	public void setStatus_msg(String status_msg) {
		this.status_msg = status_msg;
	}


	public List<Todo> getTodos() {
		return todos;
	}


	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}


	public List<Schedule> getSchedules() {
		return schedules;
	}


	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}


	public List<Performance> getPerformances() {
		return performances;
	}


	public void setPerformances(List<Performance> performances) {
		this.performances = performances;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", username=" + username + ", level="
				+ level + ", auth=" + auth + ", status_msg=" + status_msg + ", todos=" + todos + ", schedules="
				+ schedules + ", performances=" + performances + "]";
	}




	
	
	
	
	
	

}
