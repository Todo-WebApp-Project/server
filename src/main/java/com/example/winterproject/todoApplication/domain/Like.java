package com.example.winterproject.todoApplication.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="likes")
public class Like implements Serializable{

	@EmbeddedId
	private LikeId likeId;

	// 테이블들간 연관관계를 설정해 줄때 일대다(1:N) 관계일때 @JoinColumn 어노테이션을 사용해서 해당 컬럼의 이름을 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

	public LikeId getLikeId() {
		return likeId;
	}

	public void setLikeId(LikeId likeId) {
		this.likeId = likeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Like [likeId=" + likeId + ", user=" + user + ", post=" + post + "]";
	}
    
    

}
