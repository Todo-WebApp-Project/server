package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

//Sche_id, user_id, color(dcdcdc), start_date , end_date, plan 

@Entity
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUto_Increment
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	@Column(nullable = false)// not null
	@ColumnDefault("'dcdcdc'")
	private String color;
	
	@Column(nullable = false)
	private LocalDate start_date;
	
	@Column(nullable = false)
	private LocalDate end_date;
	
	@Column(nullable = false)
	private String plan;

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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", user=" + user + ", color=" + color + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", plan=" + plan + "]";
	}


}
