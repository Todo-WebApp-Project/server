package com.example.winterproject.todoApplication.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class FollowId implements Serializable {
	private String follower; // 팔로우 요청하는 유저 아이디
    private String following; // 팔로우 요청 당하는 유저 아이디
    
	public String getFollower() {
		return follower;
	}
	public void setFollower(String follower) {
		this.follower = follower;
	}
	public String getFollowing() {
		return following;
	}
	public void setFollowing(String following) {
		this.following = following;
	}
	@Override
	public String toString() {
		return "FollowId [follower=" + follower + ", following=" + following + "]";
	}

	
}
