package com.example.demo.domain.ck;

import java.io.Serializable;

public class FollowId implements Serializable {
    private String following;
    private String followed;

    public FollowId() {

    }

    public FollowId(String following, String followed) {
        this.following = following;
        this.followed = followed;
    }

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
        return "FollowId{" +
                "following='" + following + '\'' +
                ", followed='" + followed + '\'' +
                '}';
    }
}
