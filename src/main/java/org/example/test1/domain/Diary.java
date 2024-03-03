package org.example.test1.domain;

import jakarta.persistence.*;
import org.example.test1.domain.ck.LikeId;

import java.util.Date;

@Entity
public class Diary {
    @Id
    @Column(name="diary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @Column(name="no_tag")
    private Long noTag;

    @Column(name="user_id")
    private String userId;

    @Column(name="write_date")
    private Date writeDate;

    @Column(columnDefinition="TEXT")
    private String content;

    @Column(name="diary_img")
    private String diaryImg;

    public Long getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Long diaryId) {
        this.diaryId = diaryId;
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

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDiaryImg() {
        return diaryImg;
    }

    public void setDiaryImg(String diaryImg) {
        this.diaryImg = diaryImg;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "diaryId=" + diaryId +
                ", noTag=" + noTag +
                ", userId='" + userId + '\'' +
                ", writeDate=" + writeDate +
                ", content='" + content + '\'' +
                ", diaryImg='" + diaryImg + '\'' +
                '}';
    }
}
