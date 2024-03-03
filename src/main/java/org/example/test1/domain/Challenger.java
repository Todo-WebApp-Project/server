package org.example.test1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.example.test1.domain.ck.ChallengerId;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;

@Entity
@IdClass(ChallengerId.class)
public class Challenger {
    @Id
    @Column(name="no_tag")
    private Long noTag;

    @Id
    @Column(name="user_id")
    private String userId;

    private int cnt;            //챌린지 끝낸 날짜 카운트

    @Column(name="start_date")
    @CreatedDate
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    public Challenger() {

    }

    public Challenger(Long noTag, String userId, Date startDate, Date endDate) {
        this.noTag = noTag;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Challenger{" +
                "noTag=" + noTag +
                ", userId='" + userId + '\'' +
                ", cnt=" + cnt +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
