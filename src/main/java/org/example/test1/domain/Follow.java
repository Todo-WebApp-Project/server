package org.example.test1.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.example.test1.domain.ck.FollowId;

@Entity
@IdClass(FollowId.class)
public class Follow {
    @Id
    private String following;

    @Id
    private String followed;

    //(기본 생성자만 사용하면 생성자 정의를 안해도 되지만 파라미터 필요한 경우 기본 생성자도 정의해야 함.)
    public Follow(String following, String followed) {
        this.following = following;
        this.followed = followed;
    }

    public Follow() {

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
        return "Follow{" +
                "following='" + following + '\'' +
                ", followed='" + followed + '\'' +
                '}';
    }
}

