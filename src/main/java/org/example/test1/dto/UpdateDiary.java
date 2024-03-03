package org.example.test1.dto;

import jakarta.persistence.Column;
import java.util.Date;

public class UpdateDiary {
    private Long diaryId;
    @Column(columnDefinition="TEXT")
    private String content;

    private String diaryImg;

    public Long getDiaryId() {
        return diaryId;
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
        return "UpdateDiary{" +
                "diaryId=" + diaryId +
                ", content='" + content + '\'' +
                ", diaryImg=" + diaryImg +
                '}';
    }
}
