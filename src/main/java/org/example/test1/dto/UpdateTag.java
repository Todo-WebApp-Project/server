package org.example.test1.dto;

import jakarta.persistence.Column;

public class UpdateTag {

    private String tagName;
    private String tagImg;

    @Column(columnDefinition="TEXT")
    private String tagDesc;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagImg() {
        return tagImg;
    }

    public void setTagImg(String tagImg) {
        this.tagImg = tagImg;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    @Override
    public String toString() {
        return "UpdateTag{" +
                ", tagName='" + tagName + '\'' +
                ", tagImg='" + tagImg + '\'' +
                ", tagDesc='" + tagDesc + '\'' +
                '}';
    }
}
