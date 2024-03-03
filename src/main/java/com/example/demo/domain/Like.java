package com.example.demo.domain;

import com.example.demo.domain.ck.LikeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity(name="likes")
@IdClass(LikeId.class)
public class Like {
    @Id
    @Column(name="user_id")
    private String userId;

    @Id
    @Column(name="post_id")
    private Long postId;

    public Like() {

    }

    public Like(String userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Like{" +
                "userId='" + userId + '\'' +
                ", postId=" + postId +
                '}';
    }
}
