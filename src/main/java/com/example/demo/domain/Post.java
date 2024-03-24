package com.example.demo.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Post {
    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String userId;

    @Column(name="post_date")
    private Date postDate;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId='" + userId + '\'' +
                ", postDate=" + postDate +
                '}';
    }
}
