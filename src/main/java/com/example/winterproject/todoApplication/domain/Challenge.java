package com.example.winterproject.todoApplication.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Challenge {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long noTag;
	
	@JsonProperty("tag_name")
	private String tagName;
	
	@JsonProperty("tag_desc")
	private String tagDesc;
	
	@JsonProperty("main_img")
	private String mainImg;
	
	private int participants = 1; // default 1
	
	// Getters and Setters
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
	public String getMainImg() {
		return mainImg;
	}
	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}
	public int getParticipants() {
		return participants;
	}
	public void setParticipants(int participants) {
		this.participants = participants;
	}
	
	// toString
	@Override
	public String toString() {
		return "Challenge [noTag=" + noTag + ", tagName=" + tagName + ", tagDesc=" + tagDesc + ", mainImg=" + mainImg
				+ ", participants=" + participants + "]";
	}

}
