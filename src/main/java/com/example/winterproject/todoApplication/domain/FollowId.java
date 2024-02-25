package com.example.winterproject.todoApplication.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class FollowId implements Serializable {
	private String following; // 팔로우 요청하는 유저 아이디
    private String followed; // 팔로우 요청 당하는 유저 아이디
	public String getFollowing() {
		return following;
	}
	public void setFollowing(String following) {
		this.following = following;
	}
	public String getFollowed() {
		return followed;
	}
	public void setFollowed(String followed) {
		this.followed = followed;
	}
	@Override
	public String toString() {
		return "FollowId [following=" + following + ", followed=" + followed + "]";
	}
    
	
}
