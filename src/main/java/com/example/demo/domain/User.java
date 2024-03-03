package com.example.demo.domain;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity(name="users")
public class User {

    @Id
    @Column(name="user_id")
    private String userId;

    private String email;

    private String password;

    private String username;

    private int level = -1;     //(**업데이트 할지 말지 확인해야 하므로 -1)

    private int auth = -1;	    //비공개 계정(**업데이트 할지 말지 확인해야 하므로 -1)

    @Column(name="user_msg")
    private String userMsg;		//상태메시지(초기화 안하면 자동으로 null 들어감)

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

    @PrePersist                 //DB에 저장되기 전에 실행되도록 하는 어노테이션.
    public void generateUUID() {
        this.userId = UUID.randomUUID().toString();     //36자
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getUserMsg() {
        return userMsg;
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


    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public User(String userId, String email, String password,
                @Size(min = 2, message = "Name should have atleast 2 characters") String username, Integer level,
                Integer auth, String status_msg, List<Todo> todos, List<Schedule> schedules,
                List<com.example.demo.domain.Performance> performances) {
        super();
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.level = level;
        this.auth = auth;
        this.userMsg = status_msg;
        this.todos = todos;
        this.schedules = schedules;
        this.performances = performances;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", level=" + level +
                ", auth=" + auth +
                ", userMsg='" + userMsg + '\'' +
                '}';
    }
}

