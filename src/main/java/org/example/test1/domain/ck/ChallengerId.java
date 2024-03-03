package org.example.test1.domain.ck;

import jakarta.persistence.Column;
import java.io.Serializable;

public class ChallengerId implements Serializable {
    private Long noTag;

    private String userId;
    public ChallengerId() {

    }

    public ChallengerId(Long noTag, String userId) {
        this.noTag = noTag;
        this.userId = userId;
    }

    public Long getNoTag() {
        return noTag;
    }

    public void setNoTag(Long noTag) {
        this.noTag = noTag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChallengerId{" +
                "noTag=" + noTag +
                ", userId='" + userId + '\'' +
                '}';
    }
}
