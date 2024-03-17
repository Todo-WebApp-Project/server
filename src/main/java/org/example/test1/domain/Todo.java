package org.example.test1.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {
    @Id
    @Column(name="todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    private String userId;

    private String task;

    private int finish;

    @Column(name="no_tag")
    private Long noTag;

    private int share;

    @Column(name="todo_date")
    private Date todoDate;

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Long getNoTag() {
        return noTag;
    }

    public void setNoTag(Long noTag) {
        this.noTag = noTag;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public Date getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(Date todoDate) {
        this.todoDate = todoDate;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "todoId=" + todoId +
                ", userId='" + userId + '\'' +
                ", task='" + task + '\'' +
                ", finish=" + finish +
                ", noTag=" + noTag +
                ", share=" + share +
                ", todoDate=" + todoDate +
                '}';
    }
}
