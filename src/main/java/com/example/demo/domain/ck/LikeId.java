package com.example.demo.domain.ck;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;

public class LikeId implements Serializable {
    private String userId;

    private Long postId;

    public LikeId() {

    }

    public LikeId(String userId, Long postId) {
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
        return "LikeId{" +
                "userId='" + userId + '\'' +
                ", postId=" + postId +
                '}';
    }
}
