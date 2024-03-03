package org.example.test1.domain;

import jakarta.persistence.*;

@Entity
public class Challenge {
    @Id
    @Column(name="no_tag")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noTag;

    @Column(name="tag_name")
    private String tagName;

    @Column(name="tag_desc", columnDefinition="TEXT")
    private String tagDesc;

    @Column(name="tag_img")
    private String tagImg;

    private int participants;

    public Long getNoTag() {
        return noTag;
    }

    public void setNoTag(Long noTag) {
        this.noTag = noTag;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public String getTagImg() {
        return tagImg;
    }

    public void setTagImg(String tagImg) {
        this.tagImg = tagImg;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "noTag=" + noTag +
                ", tagName='" + tagName + '\'' +
                ", tagDesc='" + tagDesc + '\'' +
                ", tagImg='" + tagImg + '\'' +
                ", participants=" + participants +
                '}';
    }
}
