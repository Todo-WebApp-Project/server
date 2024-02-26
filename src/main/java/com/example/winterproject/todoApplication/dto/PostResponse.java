package com.example.winterproject.todoApplication.dto;

import java.time.LocalDate;
import java.util.List;

// 
public class PostResponse {

	private Long postId;
    private String userName;
    private LocalDate todoDate;
    private List<TaskResponse> tasks;

    // Constructor, getters, and setters
    
    public static class TaskResponse {
        private String task;
        private int finish;
        public TaskResponse(String task, int finish) {
			super();
			this.task = task;
			this.finish = finish;
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
		

    }

	public PostResponse() {
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getTodoDate() {
		return todoDate;
	}

	public void setTodoDate(LocalDate todoDate) {
		this.todoDate = todoDate;
	}

	public List<TaskResponse> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskResponse> tasks) {
		this.tasks = tasks;
	}
	
}
